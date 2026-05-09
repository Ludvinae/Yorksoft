package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.ClassificationDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ClassificationDtoUnitTest {
    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validClassificationWithNullTicket_shouldNotBeValid() {
        ClassificationDto classification = new ClassificationDto(1, null, 1, LocalDateTime.now());

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(classification),
                "idTicket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Classification should have a ticket associated");
    }

    @Test
    public void validClassificationWithNullTheme_shouldNotBeValid() {
        ClassificationDto classification = new ClassificationDto(1, 1, null, LocalDateTime.now());

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(classification),
                "idTheme",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Classification should have a theme associated");
    }

    // ---------- valid case ----------

    @Test
    public void classificationWithValidData_shouldBeValid() {
        ClassificationDto classification = new ClassificationDto(1, 1, 1, LocalDateTime.now());

        Assertions.assertTrue(
                validator.validate(classification).isEmpty(),
                "Classification should be valid"
        );
    }
}
