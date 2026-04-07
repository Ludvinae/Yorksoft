package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VersionTypeUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void versionTypeWithBlankDesignation_shouldNotBeValid() {
        VersionType type = new VersionType();
        type.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void versionTypeWithTooLongDesignation_shouldNotBeValid() {
        VersionType type = new VersionType();
        type.setDesignation("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 127 characters long");
    }


}
