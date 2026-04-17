package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Classification;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClassificationUnitTest {
    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validClassificationWithBlankTicket_shouldNotBeValid() {
        Classification classification = new Classification();
        classification.setTicket(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(classification),
                "ticket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Classification should have a ticket associated");
    }

    @Test
    public void validClassificationWithBlankTheme_shouldNotBeValid() {
        Classification classification = new Classification();
        classification.setTheme(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(classification),
                "theme",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Classification should have a theme associated");
    }

}
