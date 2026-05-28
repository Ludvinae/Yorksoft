package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.ArticleDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ArticleDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void articleWithBlankContent_shouldNotBeValid() {
        ArticleDto article = new ArticleDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "", 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content must not be blank on creation");
    }


    @Test
    public void validArticleWithNullKnowledge_shouldNotBeValid() {
        ArticleDto article = new ArticleDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "Test content", null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article),
                "idKnowledge",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Article should have a knowledge associated");
    }

    // ---------- valid case ----------

    @Test
    public void articleWithValidData_shouldBeValid() {
        ArticleDto article = new ArticleDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "Test content", 1, 1);

        Assertions.assertTrue(
                validator.validate(article).isEmpty(),
                "ArticleDto should be valid"
        );
    }
}
