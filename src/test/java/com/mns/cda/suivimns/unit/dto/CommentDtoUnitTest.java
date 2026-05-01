package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.dto.StatusDto;
import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CommentDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void commentWithBlankContent_shouldNotBeValid() {
        CommentDto comment = new CommentDto(1, "", LocalDateTime.now(),
                LocalDateTime.now(), 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(comment),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content should not be blank");
    }

    @Test
    public void validCommentWithNullTicket_shouldNotBeValid() {
        CommentDto comment = new CommentDto(1, "Test content", LocalDateTime.now(),
                LocalDateTime.now(), null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(comment),
                "idTicket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Comment should have a ticket associated");
    }

    // ---------- valid case ----------

    @Test
    public void commentWithValidData_shouldBeValid() {
        CommentDto comment = new CommentDto(1, "Test content", LocalDateTime.now(),
                LocalDateTime.now(), 1, 1);

        Assertions.assertTrue(
                validator.validate(comment).isEmpty(),
                "CommentDto should be valid"
        );
    }

}
