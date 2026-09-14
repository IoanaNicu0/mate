package org.fidami.mate;

import org.fidami.mate.loggingConfig.DoubleOutputStream;
import org.fidami.mate.loggingConfig.LoggingInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Scanner;

import static org.fidami.mate.Utils.*;

public class Main {

    private static final String CALCULATION_TEMPLATE = "%d) %s";
    static long min;
    static long max;
    static OperationEnum op;
    static int corecte = 0;
    static int gresite = 0;
    static long durata = 0;

    public static void main(String[] args) throws FileNotFoundException {
        setupLogging();
        setupLimits();

        try {
            while (true) {
                try {
                    long startTime = System.currentTimeMillis();

                    Operation operation = generateCalculation((int) min, (int) max, op);
                    write(operation.getOperationText());

                    long inputtedResult = readNumber();

                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    if (operation.getResult() == inputtedResult) {
                        corecte++;
                        write("Bravo! Correct answer.");
                    } else {
                        write("Try again: ");
                        inputtedResult = readNumber();

                        if (operation.getResult() == inputtedResult) {
                            corecte++;
                            write("Good job! Correct answer.");
                        } else {
                            gresite++;
                            write("Wrong answer. The correct answer is: " + operation.getResult());
                        }
                    }

                    durata += duration / 1000;
                    write(" (" + duration / 1000 + "s). \n\r\n\r");
                } catch (ArithmeticException supressed) {
                }
            }
        } catch (Exception e) {
            write(String.format("""
                    Summary:
                    Total: %s
                    Right answer: %s
                    Wrong answer: %s
                    Average duration: %ds
                    """, corecte + gresite, corecte, gresite,
                    (corecte + gresite) == 0 ? 0 : durata / (corecte + gresite)));
        }
    }

    public static Operation generateCalculation(int min, int max, OperationEnum operationEnum) {
        return generateCalculation(min, max, operationEnum, Utils::generateNumber);
    }

    public static Operation generateCalculation(int min, int max, OperationEnum operationEnum, NumberGenerator generator) {
        long a = generator.nextInt(min, max);
        long b = generator.nextInt(min, max);
        int nrCalcul = corecte + gresite + 1;

        Operation operation = new Operation();
        operation.setOperation(operationEnum);

        switch (operationEnum) {
            case ADDITION: {
                operation.setTerms(new Terms(a, b));
                operation.setResult(a + b);
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            case ADDITION_CARRY: {
                while (units(a) + units(b) < 10) {
                    a = generator.nextInt(min, max);
                    b = generator.nextInt(min, max);
                }
                operation.setTerms(new Terms(a, b));
                operation.setResult(a + b);
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            case MULTIPLICATION: {
                operation.setTerms(new Terms(a, b));
                operation.setResult(a * b);
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            case SUBTRACTION: {
                if (a > b) {
                    operation.setTerms(new Terms(a, b));
                } else {
                    operation.setTerms(new Terms(b, a));
                }
                operation.setResult(operation.getTerms().a() - operation.getTerms().b());
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            case SUBTRACTION_CARRY: {
                Terms termeni = assureCarryForUnitsSubstraction(a, b);

                if (termeni.a() - termeni.b() < 0) {
                    throw new ArithmeticException();
                }

                operation.setTerms(termeni);
                operation.setResult(operation.getTerms().a() - operation.getTerms().b());
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            case DIVISION: {
                if (a == 0 || b == 0) {
                    throw new ArithmeticException();
                }

                long produs = a * b;
                operation.setTerms(new Terms(produs, a));
                operation.setResult(b);
                operation.setOperationText(String.format(CALCULATION_TEMPLATE, nrCalcul, operation.print()));
                return operation;
            }
            default: throw new RuntimeException("How did i even get here?");
        }
    }

    public static OperationEnum getOperation(String s) {
        return switch (s) {
            case "+" -> OperationEnum.ADDITION;
            case "++" -> OperationEnum.ADDITION_CARRY;
            case "-" -> OperationEnum.SUBTRACTION;
            case "--" -> OperationEnum.SUBTRACTION_CARRY;
            case "x" -> OperationEnum.MULTIPLICATION;
            case ":" -> OperationEnum.DIVISION;
            default -> throw new IllegalArgumentException("Invalid operation: " + s);
        };
    }

    private static void setupLogging() throws FileNotFoundException {
        PrintStream consoleOut = System.out;
        PrintStream consoleErr = System.err;
        PrintStream fileOut = new PrintStream(new FileOutputStream("app.log", true), true);
        System.setOut(new PrintStream(new DoubleOutputStream(consoleOut, fileOut), true));
        System.setErr(new PrintStream(new DoubleOutputStream(consoleErr, fileOut), true));
        System.setIn(new LoggingInputStream(System.in, fileOut));
        fileOut.println("=============================");
        fileOut.print("New session started at ");
        fileOut.println(Instant.now().toString());
    }

    private static void setupLimits() {
        Scanner scanner = new Scanner(System.in);
        boolean validNumbers = false;

        while (!validNumbers) {
            write("min = ");
            min = readNumber();
            write("max = ");
            max = readNumber();
            validNumbers = validateLimits(min, max);
        }

        while (op == null) {
            try {
                write("calcul (+ ++ - -- x :) = ");
                op = getOperation(scanner.next());
            } catch (IllegalArgumentException e) {
                write("Operatie invalida. Mai incearca.\n");
            }
        }

    }
}
