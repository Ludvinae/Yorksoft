package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AssignmentUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void assignmentWithAllFieldsFilled_shouldBeValid() {
        Assignment assignment = new Assignment();

        assignment.setAssignmentDate(LocalDateTime.now());
        assignment.setEndDate(LocalDateTime.now());
        assignment.setTicket(new Ticket());
        assignment.setManager(new Manager());
        assignment.setTechnician(new Technician());

        boolean hasViolation = !validator.validate(assignment).isEmpty();

        Assertions.assertFalse(hasViolation, "Assignment should always be valid");
    }

    @Test
    public void validAssignmentWithBlankTicket_shouldNotBeValid() {
        Assignment assignment = new Assignment();
        assignment.setTicket(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "ticket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a ticket associated");
    }

    @Test
    public void validAssignmentWithBlankManager_shouldNotBeValid() {
        Assignment assignment = new Assignment();
        assignment.setManager(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "manager",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a manager associated");
    }

    @Test
    public void validAssignmentWithBlankTechnician_shouldNotBeValid() {
        Assignment assignment = new Assignment();
        assignment.setTechnician(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "technician",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a technician associated");
    }
}