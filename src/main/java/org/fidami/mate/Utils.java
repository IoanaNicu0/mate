package org.fidami.mate;

public class Utils {

    public static long units(long min) {
        return min % 10;
    }

    public static Terms assureCarryForUnitsSubstraction(long a, long b) {
        long unitA = units(a);
        long unitB = units(b);

        if (unitA != 0 && unitA > unitB) {
            a = a - unitA + unitB;
            b = b - unitB + unitA;
        }

        return new Terms(a, b);
    }

}
