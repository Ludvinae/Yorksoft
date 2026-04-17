package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArticleUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void createArticleWithBlankContent_shouldNotBeValid() {
        Article article = new Article();
        article.setContent("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article, OnCreate.class),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content must not be blank on creation");
    }

    @Test
    public void updateArticleWithBlankContent_shouldNotBeValid() {
        Article article = new Article();
        article.setContent("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article, OnUpdate.class),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content must not be blank on update");
    }

    @Test
    public void articleWithValidContent_shouldBeValid() {
        Article article = new Article();
        article.setContent("This is a valid article content");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article, OnCreate.class),
                "content",
                "NotBlank"
        );

        Assertions.assertFalse(constraintExists, "Content should be valid");
    }

    @Test
    public void validArticleWithBlankKnowledge_shouldNotBeValid() {
        Article article = new Article();
        article.setKnowledge(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(article),
                "knowledge",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Article should have a knowledge associated");
    }
}
