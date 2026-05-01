package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.AssignmentDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AssignmentDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void validAssignmentWithNullTicket_shouldNotBeValid() {
        AssignmentDto assignment = new AssignmentDto(1, LocalDateTime.now(), null,
                null, 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "idTicket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a ticket associated");
    }

    @Test
    public void validAssignmentWithNullManager_shouldNotBeValid() {
        AssignmentDto assignment = new AssignmentDto(1, LocalDateTime.now(), null,
                1, null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "idManager",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a manager associated");
    }

    @Test
    public void validAssignmentWithNullTechnician_shouldNotBeValid() {
        AssignmentDto assignment = new AssignmentDto(1, LocalDateTime.now(), null,
                1, 1, null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(assignment),
                "idTechnician",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Assignment should have a technician associated");
    }

    // ---------- valid case ----------

    @Test
    public void assignmentWithValidData_shouldBeValid() {
        AssignmentDto assignment = new AssignmentDto(1, LocalDateTime.now(), null,
                1, 1, 1);

        Assertions.assertTrue(
                validator.validate(assignment).isEmpty(),
                "AssignmentDto should be valid"
        );
    }
}