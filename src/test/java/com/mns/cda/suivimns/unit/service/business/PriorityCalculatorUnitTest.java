package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.service.business.PriorityCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityCalculatorUnitTest {


    private final PriorityCalculator calculator = new PriorityCalculator();

    @Test
    void lowImpactLowUrgency_shouldReturnVeryLowPriority() {
        PriorityEnum result = calculator.computePriority(1, 1, 1, 0);

        assertEquals(PriorityEnum.VERY_LOW, result);
    }

    @Test
    void lowImpactHighUrgencyWithMalus_shouldReturnVeryLowPriority() {
        PriorityEnum result = calculator.computePriority(1, 2, 1, 1);

        assertEquals(PriorityEnum.VERY_LOW, result);
    }

    @Test
    void midImpactLowUrgency_shouldReturnLowPriority() {
        PriorityEnum result = calculator.computePriority(2, 1, 1, 0);

        assertEquals(PriorityEnum.LOW, result);
    }

    @Test
    void lowImpactLowUrgencyWithImportance_shouldReturnLowPriority() {
        PriorityEnum result = calculator.computePriority(1, 1, 2, 0);

        assertEquals(PriorityEnum.LOW, result);
    }

    @Test
    void midImpactLowUrgencyWithImportance_shouldReturnMediumPriority() {
        PriorityEnum result = calculator.computePriority(2, 1, 2, 0);

        assertEquals(PriorityEnum.MEDIUM, result);
    }

    @Test
    void midImpactHighUrgencyWithMalus_shouldReturnMediumPriority() {
        PriorityEnum result = calculator.computePriority(3, 2, 1, 1);

        assertEquals(PriorityEnum.MEDIUM, result);
    }

    @Test
    void highImpactHighUrgency_shouldReturnHighPriority() {
        PriorityEnum result = calculator.computePriority(3, 2, 1, 0);

        assertEquals(PriorityEnum.HIGH, result);
    }

    @Test
    void highImpactLowUrgencyWithMalus_shouldReturnHighPriority() {
        PriorityEnum result = calculator.computePriority(4, 2, 1, 1);

        assertEquals(PriorityEnum.HIGH, result);
    }

    @Test
    void highImpactHighUrgency_shouldReturnVeryHighPriority() {
        PriorityEnum result = calculator.computePriority(4, 2, 1, 0);

        assertEquals(PriorityEnum.VERY_HIGH, result);
    }

    @Test
    void highImpactHighUrgencyWithImportance_shouldReturnVeryHighPriority() {
        PriorityEnum result = calculator.computePriority(4, 2, 3, 0);

        assertEquals(PriorityEnum.VERY_HIGH, result);
    }

    @Test
    void impactShouldBeCappedAtMax() {
        PriorityEnum result = calculator.computePriority(4, 2, 3, 0);

        assertEquals(PriorityEnum.VERY_HIGH, result);
    }

    @Test
    void urgencyShouldNotGoBelowMinimum() {
        PriorityEnum result = calculator.computePriority(1, 1, 0, 5);

        assertEquals(PriorityEnum.VERY_LOW, result);
    }

}
