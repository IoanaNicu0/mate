package org.fidami.mate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsValidationTest {

    @Test
    void validateAcceptsDescendingValues() {
        assertTrue(Utils.validateLimits(1, 10));
    }

    @Test
    void validateLimitsDoesNotAcceptAscendingValues() {
        assertFalse(Utils.validateLimits(10, 1));
    }

    @Test
    void validateLimitsDoesNotAcceptEqualValues() {
        assertFalse(Utils.validateLimits(10, 10));
    }

    @Test
    void assureCarryForUnitsSubstractionSwapsUnitsWhenNeeded() {
        Terms terms = Utils.assureCarryForUnitsSubstraction(57, 23);

        assertEquals(new Terms(53, 27), terms);
    }

    @Test
    void assureCarryForUnitsSubstractionKeepsValuesWhenUnitIsZero() {
        Terms terms = Utils.assureCarryForUnitsSubstraction(50, 23);

        assertEquals(new Terms(50, 23), terms);
    }

    @Test
    void assureCarryForUnitsSubstractionKeepsValuesWhenUnitIsZero1() {
        Terms terms = Utils.assureCarryForUnitsSubstraction(48, 56);

        assertEquals(new Terms(56, 48), terms);
    }
}

