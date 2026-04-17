package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.model.Assignment;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AssignmentUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void assignmentWithEmptyData_shouldBeValid() {
        Assignment assignment = new Assignment();

        boolean hasViolation = !validator.validate(assignment).isEmpty();

        Assertions.assertFalse(hasViolation, "Assignment should be valid because no constraints are defined");
    }

    @Test
    public void assignmentWithAllFieldsFilled_shouldBeValid() {
        Assignment assignment = new Assignment();

        assignment.setAssignmentDate(null); // auto généré normalement
        assignment.setEndDate(null);

        boolean hasViolation = !validator.validate(assignment).isEmpty();

        Assertions.assertFalse(hasViolation, "Assignment should always be valid");
    }
}