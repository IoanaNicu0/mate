package org.fidami.mate;

public enum Operation {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION;

    @Override
    public String toString() {
        return switch (this) {
            case ADDITION -> "+";
            case SUBTRACTION -> "-";
            case MULTIPLICATION -> "x";
            case DIVISION -> ":";
        };
    }

}
