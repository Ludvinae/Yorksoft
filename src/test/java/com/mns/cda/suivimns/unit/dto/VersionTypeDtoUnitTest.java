package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.VersionTypeDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VersionTypeDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void versionTypeWithBlankDesignation_shouldNotBeValid() {
        VersionTypeDto type = new VersionTypeDto(1, "", (byte) 0);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void versionTypeWithTooLongDesignation_shouldNotBeValid() {
        String designation = "a".repeat(128);
        VersionTypeDto type = new VersionTypeDto(1, designation, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 127 characters long");
    }

    // ---------- valid case ----------

    @Test
    public void versionTypeWithValidData_shouldBeValid() {
        VersionTypeDto type = new VersionTypeDto(1, "Test designation", (byte) 0);

        Assertions.assertTrue(
                validator.validate(type).isEmpty(),
                "VersionTypeDto should be valid"
        );
    }

}
