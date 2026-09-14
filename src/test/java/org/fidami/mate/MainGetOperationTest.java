package org.fidami.mate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainGetOperationTest {

    @Test
    void mapsAdditionSymbols() {
        assertEquals(OperationEnum.ADDITION, Main.getOperation("+"));
        assertEquals(OperationEnum.ADDITION_CARRY, Main.getOperation("++"));
    }

    @Test
    void mapsSubtractionSymbols() {
        assertEquals(OperationEnum.SUBTRACTION, Main.getOperation("-"));
        assertEquals(OperationEnum.SUBTRACTION_CARRY, Main.getOperation("--"));
    }

    @Test
    void mapsMultiplicationAndDivisionSymbols() {
        assertEquals(OperationEnum.MULTIPLICATION, Main.getOperation("x"));
        assertEquals(OperationEnum.DIVISION, Main.getOperation(":"));
    }

    @Test
    void throwsForInvalidOperation() {
        assertThrows(IllegalArgumentException.class, () -> Main.getOperation("/"));
    }
}

