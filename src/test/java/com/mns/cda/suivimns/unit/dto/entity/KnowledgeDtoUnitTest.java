package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void KnowledgeWithBlankSubject_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        KnowledgeDto knowledge = new KnowledgeDto(1, "", 1, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge),
                "subject",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Subject should not be blank");
    }

    @Test
    public void knowledgeWithTooLongSubject_shouldNotBeValid() {
        String subject = "a".repeat(256);
        List<Integer> list = new ArrayList<>();
        KnowledgeDto knowledge = new KnowledgeDto(1, subject, 1, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge),
                "subject",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Subject must be <= 255 characters");
    }

    @Test
    public void validKnowledgeWithBlankTheme_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        KnowledgeDto knowledge = new KnowledgeDto(1, "Test subject", null, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge),
                "idTheme",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Knowledge should have a theme associated");
    }

    // ---------- valid case ----------

    @Test
    public void knowledgeWithValidData_shouldBeValid() {
        List<Integer> list = new ArrayList<>();
        KnowledgeDto knowledge = new KnowledgeDto(1, "Test subject", 1, list, list);

        Assertions.assertTrue(
                validator.validate(knowledge).isEmpty(),
                "KnowledgeDto should be valid"
        );
    }
}
