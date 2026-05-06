package com.mns.cda.suivimns.unit.service.business;

import com.mns.cda.suivimns.enumerate.Priority;
import com.mns.cda.suivimns.service.business.PriorityCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityCalculatorUnitTest {


    private final PriorityCalculator calculator = new PriorityCalculator();

    @Test
    void lowImpactLowUrgency_shouldReturnVeryLowPriority() {
        Priority result = calculator.computePriority(1, 1, 1, 0);

        assertEquals(Priority.VERY_LOW, result);
    }

    @Test
    void lowImpactHighUrgencyWithMalus_shouldReturnVeryLowPriority() {
        Priority result = calculator.computePriority(1, 2, 1, 1);

        assertEquals(Priority.VERY_LOW, result);
    }

    @Test
    void midImpactLowUrgency_shouldReturnLowPriority() {
        Priority result = calculator.computePriority(2, 1, 1, 0);

        assertEquals(Priority.LOW, result);
    }

    @Test
    void lowImpactLowUrgencyWithImportance_shouldReturnLowPriority() {
        Priority result = calculator.computePriority(1, 1, 2, 0);

        assertEquals(Priority.LOW, result);
    }

    @Test
    void midImpactLowUrgencyWithImportance_shouldReturnMediumPriority() {
        Priority result = calculator.computePriority(2, 1, 2, 0);

        assertEquals(Priority.MEDIUM, result);
    }

    @Test
    void midImpactHighUrgencyWithMalus_shouldReturnMediumPriority() {
        Priority result = calculator.computePriority(3, 2, 1, 1);

        assertEquals(Priority.MEDIUM, result);
    }

    @Test
    void highImpactHighUrgency_shouldReturnHighPriority() {
        Priority result = calculator.computePriority(3, 2, 1, 0);

        assertEquals(Priority.HIGH, result);
    }

    @Test
    void highImpactLowUrgencyWithMalus_shouldReturnHighPriority() {
        Priority result = calculator.computePriority(4, 2, 1, 1);

        assertEquals(Priority.HIGH, result);
    }

    @Test
    void highImpactHighUrgency_shouldReturnVeryHighPriority() {
        Priority result = calculator.computePriority(4, 2, 1, 0);

        assertEquals(Priority.VERY_HIGH, result);
    }

    @Test
    void highImpactHighUrgencyWithImportance_shouldReturnVeryHighPriority() {
        Priority result = calculator.computePriority(4, 2, 3, 0);

        assertEquals(Priority.VERY_HIGH, result);
    }

    @Test
    void impactShouldBeCappedAtMax() {
        Priority result = calculator.computePriority(4, 2, 3, 0);

        assertEquals(Priority.VERY_HIGH, result);
    }

    @Test
    void urgencyShouldNotGoBelowMinimum() {
        Priority result = calculator.computePriority(1, 1, 0, 5);

        assertEquals(Priority.VERY_LOW, result);
    }

}
