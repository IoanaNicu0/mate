package org.fidami.mate;

import java.util.Random;
import java.util.Scanner;

public class Utils {

    public static int generateNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static void write(Object a) {
        System.out.print(a);
    }

    public static long readNumber() {
        Scanner scanner = new Scanner(System.in);
        String readString;
        long number = -1;

        while (number == -1) {
            readString = scanner.next();

            if (readString.equals("exit")) {
                throw new RuntimeException("Exit signal detected.");
            }

            try {
                number = Long.parseLong(readString);
            } catch (NumberFormatException e) {
                //supress
            }
        }

        return number;
    }

    public static long units(long number) {
        return number % 10;
    }

    public static long tens(long number) {
        return (number / 10) % 10;
    }

    public static Terms assureCarryForUnitsSubstraction(long a, long b) {
        if (a < b) {
            a = a + b;
            b = a - b;
            a = a - b;
        }

        long unitA = units(a);
        long unitB = units(b);

        if (tens(a) == tens(b)) {
            a += 10;
        }

        if (unitA != 0 && unitA > unitB) {
            a = a - unitA + unitB;
            b = b - unitB + unitA;
        }

        return new Terms(a, b);
    }

    public static boolean validateLimits(long a, long b) {
        if (a == b) {
            write("Limitele nu pot fi egale.\n");
        }

        if (a > b) {
            write("Minimul nu poate fi mai mare decat maximul.\n");
        }

        return a < b;
    }
}
