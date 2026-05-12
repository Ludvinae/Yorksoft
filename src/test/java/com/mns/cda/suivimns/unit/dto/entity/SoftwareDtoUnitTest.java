package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.SoftwareDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class SoftwareDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validSoftwareWithBlankName_shouldNotBeValid() {
        SoftwareDto software = new SoftwareDto(1, "", "Test description", 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(software),
                "name",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Software name should not be blank");
    }

    @Test
    public void validSoftwareWithTooLongName_shouldNotBeValid() {
        String name = "a".repeat(128);
        SoftwareDto software = new SoftwareDto(1, name, "Test description", 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(software),
                "name",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Software name should be at maximum 127 characters long");
    }

    // ---------- valid case ----------

    @Test
    public void themeWithValidData_shouldBeValid() {
        SoftwareDto software = new SoftwareDto(1, "Test name", "Test description", 1);

        Assertions.assertTrue(
                validator.validate(software).isEmpty(),
                "SoftwareDto should be valid"
        );
    }

}
