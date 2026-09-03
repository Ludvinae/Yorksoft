package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.StatusDto;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StatusDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void statusWithBlankDesignation_shouldNotBeValid() {
        StatusDto status = new StatusDto(1, "", StatusEnum.OPEN, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void statusWithTooLongDesignation_shouldNotBeValid() {
        String designation = "a".repeat(64);
        StatusDto status = new StatusDto(1, designation, StatusEnum.OPEN, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 63 characters long");
    }

    @Test
    public void statusWithTooShortDesignation_shouldNotBeValid() {
        StatusDto status = new StatusDto(1, "T", StatusEnum.OPEN, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at least 3 characters long");
    }

    @Test
    public void statusWithNullCode_shouldNotBeValid() {
        StatusDto status = new StatusDto(1, "Test designation", null, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "code",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Code must not be null");
    }


    // ---------- valid case ----------

    @Test
    public void statusWithValidData_shouldBeValid() {
        StatusDto status = new StatusDto(1, "Test designation", StatusEnum.OPEN, (byte) 1);

        Assertions.assertTrue(
                validator.validate(status).isEmpty(),
                "StatusDto should be valid"
        );
    }
}
