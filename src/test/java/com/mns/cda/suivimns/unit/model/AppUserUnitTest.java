package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppUserUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // ---------- firstName ----------

    @Test
    public void appUserWithTooLongFirstName_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser),
                "firstName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "FirstName must be <= 127 characters");
    }

    // ---------- lastName ----------

    @Test
    public void appUserWithTooLongLastName_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setLastName("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser),
                "lastName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "LastName must be <= 127 characters");
    }

    // ---------- email ----------

    @Test
    public void appUserWithInvalidEmail_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setEmail("invalid-email");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser, OnCreate.class),
                "email",
                "Email"
        );

        Assertions.assertTrue(constraintExists, "Email must be valid");
    }

    @Test
    public void appUserWithTooLongEmail_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setEmail("a".repeat(128) + "@test.com");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser),
                "email",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Email must be <= 127 characters");
    }

    // ---------- phoneNumber ----------

    @Test
    public void appUserWithTooLongPhoneNumber_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setPhoneNumber("1".repeat(32));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser),
                "phoneNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Phone number must be <= 31 characters");
    }

    // ---------- password ----------

    @Test
    public void appUserWithBlankPassword_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setPassword("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser, OnCreate.class),
                "password",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Password must not be blank");
    }

    @Test
    public void appUserWithTooLongPassword_shouldNotBeValid() {
        AppUser appUser = new AppUser();
        appUser.setPassword("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(appUser),
                "password",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Password must be <= 127 characters");
    }

    // ---------- valid case ----------

    @Test
    public void appUserWithValidData_shouldBeValid() {
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setEmail("john.doe@test.com");
        appUser.setPhoneNumber("0123456789");
        appUser.setPassword("securePassword");

        boolean hasViolation = !validator.validate(appUser).isEmpty();

        Assertions.assertFalse(hasViolation, "AppUser should be valid");
    }
}
