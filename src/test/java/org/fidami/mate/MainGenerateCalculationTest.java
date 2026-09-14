package org.fidami.mate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainGenerateCalculationTest {

    @BeforeEach
    void resetCounters() {
        Main.corecte = 0;
        Main.gresite = 0;
    }

    @Test
    void buildsAdditionCalculation() {
        Operation operation = Main.generateCalculation(4, 4, OperationEnum.ADDITION);

        assertEquals(new Terms(4, 4), operation.getTerms());
        assertEquals(8L, operation.getResult());
        assertEquals(OperationEnum.ADDITION, operation.getOperation());
        assertEquals("1) 4 + 4 = ", operation.getOperationText());
    }

    @Test
    void buildsAdditionCarryCalculationWithCarryInUnits() {
        Operation operation = Main.generateCalculation(5, 9, OperationEnum.ADDITION_CARRY);

        assertTrue(Utils.units(operation.getTerms().a()) + Utils.units(operation.getTerms().b()) >= 10);
        assertEquals(operation.getTerms().a() + operation.getTerms().b(), operation.getResult());
    }

    @Test
    void buildsMultiplicationCalculation() {
        Operation operation = Main.generateCalculation(3, 3, OperationEnum.MULTIPLICATION);

        assertEquals(new Terms(3, 3), operation.getTerms());
        assertEquals(9L, operation.getResult());
        assertEquals("1) 3 x 3 = ", operation.getOperationText());
    }

    @Test
    void buildsSubtractionCalculation() {
        Operation operation = Main.generateCalculation(9, 9, OperationEnum.SUBTRACTION);

        assertEquals(new Terms(9, 9), operation.getTerms());
        assertEquals(0L, operation.getResult());
        assertEquals("1) 9 - 9 = ", operation.getOperationText());
    }

    @Test
    void buildsSubtractionCarryCalculation() {
        Operation operation = Main.generateCalculation(17, 17, OperationEnum.SUBTRACTION_CARRY);

        assertEquals(new Terms(27, 17), operation.getTerms());
        assertEquals(10L, operation.getResult());
        assertEquals("1) 27 - 17 = ", operation.getOperationText());
    }

    @Test
    void buildsDivisionCalculation() {
        Operation operation = Main.generateCalculation(2, 2, OperationEnum.DIVISION);

        assertEquals(new Terms(4, 2), operation.getTerms());
        assertEquals(2L, operation.getResult());
        assertEquals("1) 4 : 2 = ", operation.getOperationText());
    }

    @Test
    void throwsForDivisionByZero() {
        assertThrows(ArithmeticException.class,
                () -> Main.generateCalculation(0, 0, OperationEnum.DIVISION));
    }

    @Test
    void usesCurrentCountersForCalculationIndex() {
        Main.corecte = 2;
        Main.gresite = 3;

        Operation operation = Main.generateCalculation(1, 1, OperationEnum.ADDITION);

        assertTrue(operation.getOperationText().startsWith("6) "));
    }

    @Test
    void addition_usesControlledValues() {
        AtomicInteger idx = new AtomicInteger(0);
        int[] values = {4, 7};

        NumberGenerator mockedNumberGenerator = (min, max) -> values[idx.getAndIncrement()];

        Operation op = Main.generateCalculation(1, 100, OperationEnum.ADDITION, mockedNumberGenerator);

        assertEquals(new Terms(4, 7), op.getTerms());
        assertEquals(11L, op.getResult());
    }

    @Test
    void substractionCarry_adds10IfTensAreEqual() {
        AtomicInteger idx = new AtomicInteger(0);
        int[] values = {4, 7};

        NumberGenerator mockedNumberGenerator = (min, max) -> values[idx.getAndIncrement()];

        Operation op = Main.generateCalculation(1, 100, OperationEnum.SUBTRACTION_CARRY, mockedNumberGenerator);

        assertEquals(new Terms(14, 7), op.getTerms());
        assertEquals(7L, op.getResult());
    }
}

