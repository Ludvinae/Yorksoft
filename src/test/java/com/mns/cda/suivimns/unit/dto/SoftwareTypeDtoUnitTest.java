package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SoftwareTypeDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validTypeWithBlankDesignation_shouldNotBeValid() {
        SoftwareTypeDto type = new SoftwareTypeDto(1, "");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation field must not be blank");
    }

    @Test
    public void validTypeWithTooShortDesignation_shouldNotBeValid() {
        SoftwareTypeDto type = new SoftwareTypeDto(1, "T");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation field must be at least 3 characters long");
    }

    @Test
    public void validTypeWithTooLongDesignation_shouldNotBeValid() {
        String designation = "a".repeat(128);
        SoftwareTypeDto type = new SoftwareTypeDto(1, designation);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size");

        Assertions.assertTrue(constraintExists, "Designation field must be a maximum of 127 characters long");
    }

    // ---------- valid case ----------

    @Test
    public void softwareTypeWithValidData_shouldBeValid() {
        SoftwareTypeDto type = new SoftwareTypeDto(1, "Test designation");

        Assertions.assertTrue(
                validator.validate(type).isEmpty(),
                "SoftwareTypeDto should be valid"
        );
    }

}
