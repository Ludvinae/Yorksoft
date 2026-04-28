package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ImpactUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void impactWithBlankCreatedDesignation_shouldNotBeValid() {
        Impact impact = new Impact();
        impact.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact, OnCreate.class),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation should not be blank");
    }

    @Test
    public void impactWithToolongDesignation_shouldNotBeValid() {
        Impact impact = new Impact();
        impact.setDesignation("a".repeat(70));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation should be at most 63 characters long");
    }

    @Test
    public void validImpactWithBlankPriorityFactor_shouldNotBeValid() {
        Impact impact = new Impact();
        impact.setPriorityFactor(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact),
                "priorityFactor",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Impact priorityFactor should not be blank");
    }
}
