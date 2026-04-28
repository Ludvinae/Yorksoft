package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UrgencyUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void urgencyWithBlankCreatedDesignation_shouldNotBeValid() {
        Urgency urgency = new Urgency();
        urgency.setDesignation("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency, OnCreate.class),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation should not be blank");
    }

    @Test
    public void urgencyWithToolongDesignation_shouldNotBeValid() {
        Urgency urgency = new Urgency();
        urgency.setDesignation("a".repeat(70));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation should be at most 63 characters long");
    }

    @Test
    public void urgencyWithNullPriorityFactor_shouldNotBeValid() {
        Urgency urgency = new Urgency();
        urgency.setPriorityFactor(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(urgency),
                "priorityFactor",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Urgency Priority factor must not be blank");
    }
}

