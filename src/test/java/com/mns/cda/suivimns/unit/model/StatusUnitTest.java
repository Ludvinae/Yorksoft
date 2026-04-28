package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StatusUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void statusWithBlankDesignation_shouldNotBeValid() {
        Status status = new Status();
        status.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status, OnCreate.class),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation must not be blank");
    }

    @Test
    public void statusWithTooLongDesignation_shouldNotBeValid() {
        Status status = new Status();
        status.setDesignation("a".repeat(64));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at most 63 characters long");
    }

    @Test
    public void statusWithTooShortDesignation_shouldNotBeValid() {
        Status status = new Status();
        status.setDesignation("a");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(status),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation must be at least 3 characters long");
    }
}
