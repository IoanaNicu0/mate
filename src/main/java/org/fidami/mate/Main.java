package org.fidami.mate;

import org.fidami.mate.loggingConfig.DoubleOutputStream;
import org.fidami.mate.loggingConfig.LoggingInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static long min;
    static long max;
    static Operation op;
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

                    long result = generateCalculation((int) min, (int) max, op);
                    long inputtedResult = readNumber();

                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;

                    if (result == inputtedResult) {
                        corecte++;
                        write("Bravo! Correct answer.");
                    } else {
                        write("Try again: ");
                        inputtedResult = readNumber();

                        if (result == inputtedResult) {
                            corecte++;
                            write("Good job! Correct answer.");
                        } else {
                            gresite++;
                            write("Wrong answer. The correct answer is: " + result);
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
                    Right answer: %s
                    Wrong answer: %s
                    Average duration: %ds
                    """, corecte, gresite, durata / (corecte + gresite)));
        }
    }

    public static long generateCalculation(int min, int max, Operation operation) {
        long a = generateNumber(min, max);
        long b = generateNumber(min, max);

        switch (operation) {
            case ADDITION: {
                String writtenOperation = String.format("%d %s %d = ", a, operation, b);
                write(writtenOperation);
                return a + b;
            }
            case MULTIPLICATION: {
                String writtenOperation = String.format("%d %s %d = ", a, operation, b);
                write(writtenOperation);
                return a * b;
            }
            case SUBTRACTION: {
                if (a > b) {
                    write(String.format("%d %s %d = ", a, operation, b));
                    return a - b;
                } else {
                    write(String.format("%d %s %d = ", b, operation, a));
                    return b - a;
                }
            }
            case DIVISION: {
                if (a == 0 && b == 0) throw new ArithmeticException();
                long produs = a * b;
                if (b == 0) {
                    write(String.format("%d : %d = ", produs, a));
                    return b;
                } else {
                    write(String.format("%d : %d = ", produs, b));
                    return a;
                }
            }
        }
        throw new RuntimeException("How did i even get here?");
    }

    public static int generateNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static void write(Object a) {
        System.out.print(a);
    }

    public static long readNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }

    public static Operation getOperation(String s) {
        return switch (s) {
            case "+" -> Operation.ADDITION;
            case "-" -> Operation.SUBTRACTION;
            case "x" -> Operation.MULTIPLICATION;
            case ":" -> Operation.DIVISION;
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
        write("min = ");
        min = readNumber();
        write("max = ");
        max = readNumber();
        write("calcul (+ - x :) = ");
        op = getOperation(scanner.next());
    }
}