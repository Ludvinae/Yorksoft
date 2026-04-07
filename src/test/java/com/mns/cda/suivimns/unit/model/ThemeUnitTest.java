package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ThemeUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void themeWithBlankCreatedDesignation_shouldNotBeValid() {
        Theme theme = new Theme();
        theme.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme, OnCreate.class),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void themeWithTooShortDesignation_shouldNotBeValid() {
        Theme theme = new Theme();
        theme.setDesignation("a");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at least 3 characters long");
    }

    @Test
    public void themeWithTooLongDesignation_shouldNotBeValid() {
        Theme theme = new Theme();
        theme.setDesignation("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(theme),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 127 characters long");
    }
}
