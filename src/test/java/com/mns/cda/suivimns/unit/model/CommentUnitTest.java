package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommentUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void commentWithBlankUpdatedContent_shouldNotBeValid() {
        Comment comment = new Comment();
        comment.setContent("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(comment, OnUpdate.class),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content should not be blank");
    }

    @Test
    public void commentWithBlankCreatedContent_shouldNotBeValid() {
        Comment comment = new Comment();
        comment.setContent("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(comment, OnCreate.class),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content should not be blank");
    }

    @Test
    public void validCommentWithBlankTicket_shouldNotBeValid() {
        Comment comment = new Comment();
        comment.setTicket(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(comment),
                "ticket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Comment should have a ticket associated");
    }

}
