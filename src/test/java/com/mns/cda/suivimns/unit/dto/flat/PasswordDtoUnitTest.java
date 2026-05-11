package com.mns.cda.suivimns.unit.dto.flat;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PasswordDtoUnitTest {
    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // ---------- old password ----------

    @Test
    public void passwordWithBlankOldPassword_shouldNotBeValid() {
        PasswordDto password = new PasswordDto("", "Test new password");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "oldPassword",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Password must not be blank");
    }

    @Test
    public void passwordWithTooLongOldPassword_shouldNotBeValid() {
        String pw = "a".repeat(128);
        PasswordDto password = new PasswordDto(pw, "Test new password");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "oldPassword",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Password must be <= 127 characters");
    }

    @Test
    public void passwordWithTooShortOldPassword_shouldNotBeValid() {
        PasswordDto password = new PasswordDto("old", "Test new password");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "oldPassword",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Password must be <= 127 characters");
    }

    // ---------- old password ----------

    @Test
    public void passwordWithBlankNewPassword_shouldNotBeValid() {
        PasswordDto password = new PasswordDto("Test old password", "");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "newPassword",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Password must not be blank");
    }

    @Test
    public void passwordWithTooLongNewPassword_shouldNotBeValid() {
        String pw = "a".repeat(128);
        PasswordDto password = new PasswordDto("Test old password", pw);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "newPassword",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Password must be <= 127 characters");
    }

    @Test
    public void passwordWithTooShortNewPassword_shouldNotBeValid() {
        PasswordDto password = new PasswordDto("Test old password", "new");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(password),
                "newPassword",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Password must be <= 127 characters");
    }

    // ---------- valid case ----------

    @Test
    public void appUserWithValidData_shouldBeValid() {
        PasswordDto password = new PasswordDto("Test old password", "Test new password");

        Assertions.assertTrue(
                validator.validate(password).isEmpty(),
                "PasswordDto should be valid"
        );
    }
}
