package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ThemeDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void themeWithBlankDesignation_shouldNotBeValid() {
        ThemeDto theme = new ThemeDto(1, "", "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void themeWithTooShortDesignation_shouldNotBeValid() {
        ThemeDto theme = new ThemeDto(1, "T", "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at least 3 characters long");
    }

    @Test
    public void themeWithTooLongDesignation_shouldNotBeValid() {
        String designation = "a".repeat(128);
        ThemeDto theme = new ThemeDto(1, designation, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 127 characters long");
    }

    // ---------- valid case ----------

    @Test
    public void themeWithValidData_shouldBeValid() {
        ThemeDto theme = new ThemeDto(1, "Test designation", "Test description");

        Assertions.assertTrue(
                validator.validate(theme).isEmpty(),
                "ThemeDto should be valid"
        );
    }
}
