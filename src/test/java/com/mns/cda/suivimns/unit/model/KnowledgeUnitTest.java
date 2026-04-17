package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class KnowledgeUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void KnowledgeWithBlankCreatedSubject_shouldNotBeValid() {
        Knowledge knowledge = new Knowledge();
        knowledge.setSubject("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge, OnCreate.class),
                "subject",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Subject should not be blank");
    }

    @Test
    public void KnowledgeWithBlankupdatedSubject_shouldNotBeValid() {
        Knowledge knowledge = new Knowledge();
        knowledge.setSubject("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge, OnUpdate.class),
                "subject",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Subject should not be blank");
    }

    @Test
    public void knowledgeWithTooLongSubject_shouldNotBeValid() {
        Knowledge knowledge = new Knowledge();
        knowledge.setSubject("a".repeat(256) + "@test.com");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge),
                "subject",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Subject must be <= 255 characters");
    }

    @Test
    public void validKnowledgeWithBlankTheme_shouldNotBeValid() {
        Knowledge knowledge = new Knowledge();
        knowledge.setTheme(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(knowledge),
                "theme",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Knowledge should have a theme associated");
    }
}
