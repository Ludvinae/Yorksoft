package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ManagerDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    // ---------- firstName ----------

    @Test
    public void managerWithTooLongFirstName_shouldNotBeValid() {
        String firstName = "a".repeat(128);
        ManagerDto user = new ManagerDto(1, firstName, "Test last name",
                "Test@test.com", "Test phone number");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "firstName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "FirstName must be <= 127 characters");
    }

    // ---------- lastName ----------

    @Test
    public void managerWithTooLongLastName_shouldNotBeValid() {
        String lastName = "a".repeat(128);
        ManagerDto user = new ManagerDto(1, "Test first name", lastName,
                "Test@test.com", "Test phone number");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "lastName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "LastName must be <= 127 characters");
    }

    // ---------- email ----------

    @Test
    public void managerWithInvalidEmail_shouldNotBeValid() {
        ManagerDto user = new ManagerDto(1, "Test first name", "Test last name",
                "Test", "Test phone number");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "Email"
        );

        Assertions.assertTrue(constraintExists, "Email must be valid");
    }

    @Test
    public void managerWithTooLongEmail_shouldNotBeValid() {
        String email = "a".repeat(128) + "@test.com";
        ManagerDto user = new ManagerDto(1, "Test first name", "Test last name",
                email, "Test phone number");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Email must be <= 127 characters");
    }

    // ---------- phoneNumber ----------

    @Test
    public void managerWithTooLongPhoneNumber_shouldNotBeValid() {
        String phone = "1".repeat(32);
        ManagerDto user = new ManagerDto(1, "Test first name", "Test last name",
                "Test@test.com", phone);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "phoneNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Phone number must be <= 31 characters");
    }


    // ---------- valid case ----------

    @Test
    public void managerWithValidData_shouldBeValid() {
        ManagerDto user = new ManagerDto(1, "Test first name", "Test last name",
                "Test@test.com", "Test phone number");

        Assertions.assertTrue(
                validator.validate(user).isEmpty(),
                "ManagerDto should be valid"
        );
    }
}
