package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.UrgencyDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UrgencyDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void urgencyWithBlankCreatedDesignation_shouldNotBeValid() {
        UrgencyDto urgency = new UrgencyDto(1, "", (byte) 1, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation should not be blank");
    }

    @Test
    public void urgencyWithTooLongDesignation_shouldNotBeValid() {

        String designation = "a".repeat(70);
        UrgencyDto urgency = new UrgencyDto(1, designation, (byte) 1, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation should be at most 63 characters long");
    }

    @Test
    public void urgencyWithNullPriorityFactor_shouldNotBeValid() {
        UrgencyDto urgency = new UrgencyDto(1, "Test designation", null, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency),
                "priorityFactor",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Urgency Priority factor must not be blank");
    }

    // ---------- valid case ----------

    @Test
    public void urgencyWithValidData_shouldBeValid() {
        UrgencyDto urgency = new UrgencyDto(1, "Test designation", (byte) 1, "Test description");

        Assertions.assertTrue(
                validator.validate(urgency).isEmpty(),
                "UrgencyDto should be valid"
        );
    }
}

