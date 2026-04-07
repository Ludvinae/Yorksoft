package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.SoftwareType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SoftwareTypeUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validTypeWithBlankDesignation_shouldNotBeValid() {
        SoftwareType type = new SoftwareType();
        type.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation field must not be blank");
    }

    @Test
    public void validTypeWithTooShortDesignation_shouldNotBeValid() {
        SoftwareType type = new SoftwareType();
        type.setDesignation("F");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation field must be at least 3 characters long");
    }

    @Test
    public void validTypeWithTooLongDesignation_shouldNotBeValid() {
        SoftwareType type = new SoftwareType();
        type.setDesignation("Fjdlksqjdklsqjdlksqjdlkjqslkdjsqkdjsqkdjlkdjsqidjzaoijdiozajdiajdizaj" +
                "idjazoidazdihaziudzaiudgzaydgzagdzaydgzaydgzaydgzaydgzadfshgdgwbc;nlkjaicsjqlkcj" +
                "lfdsjkfdjslkfnvvyvyuioeiuoiauoieakknskdqs667676767676868768767567576576567sqlkcjsqlc");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(type),
                "designation",
                "Size");

        Assertions.assertTrue(constraintExists, "Designation field must be a maximum of 127 characters long");
    }

}
