package org.fidami.mate;

public enum OperationEnum {
    ADDITION,
    ADDITION_CARRY,
    SUBTRACTION,
    SUBTRACTION_CARRY,
    MULTIPLICATION,
    DIVISION;

    @Override
    public String toString() {
        return switch (this) {
            case ADDITION, ADDITION_CARRY -> "+";
            case SUBTRACTION, SUBTRACTION_CARRY -> "-";
            case MULTIPLICATION -> "x";
            case DIVISION -> ":";
        };
    }

}
