package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.ClientDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    // ---------- firstName ----------

    @Test
    public void clientWithTooLongFirstName_shouldNotBeValid() {
        String firstName = "a".repeat(128);
        ClientDto user = new ClientDto(1, firstName, "Test last name",
                "Test@test.com", "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "firstName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "FirstName must be <= 127 characters");
    }

    // ---------- lastName ----------

    @Test
    public void clientWithTooLongLastName_shouldNotBeValid() {
        String lastName = "a".repeat(128);
        ClientDto user = new ClientDto(1, "Test first name", lastName,
                "Test@test.com", "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "lastName",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "LastName must be <= 127 characters");
    }

    // ---------- email ----------

    @Test
    public void clientWithBlankEmail_shouldNotBeValid() {
        ClientDto user = new ClientDto(1, "Test first name", "Test last name",
                "", "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "NotBlank"
        );
        Assertions.assertTrue(constraintExists, "Email address must not be blank");
    }

    @Test
    public void clientWithInvalidEmail_shouldNotBeValid() {
        ClientDto user = new ClientDto(1, "Test first name", "Test last name",
                "Test", "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "Email"
        );

        Assertions.assertTrue(constraintExists, "Email must be valid");
    }

    @Test
    public void clientWithTooLongEmail_shouldNotBeValid() {
        String email = "a".repeat(128) + "@test.com";
        ClientDto user = new ClientDto(1, "Test first name", "Test last name",
                email, "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Email must be <= 127 characters");
    }

    // ---------- phoneNumber ----------

    @Test
    public void clientWithTooLongPhoneNumber_shouldNotBeValid() {
        String phone = "1".repeat(32);
        ClientDto user = new ClientDto(1, "Test first name", "Test last name",
                "Test@test.com", phone, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "phoneNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Phone number must be <= 31 characters");
    }


    // ---------- valid case ----------

    @Test
    public void clientWithValidData_shouldBeValid() {
        ClientDto user = new ClientDto(1, "Test first name", "Test last name",
                "Test@test.com", "Test phone number", (byte) 1);

        Assertions.assertTrue(
                validator.validate(user).isEmpty(),
                "ClientDto should be valid"
        );
    }
}