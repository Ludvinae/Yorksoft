package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.TechnicianDto;
import com.mns.cda.suivimns.dto.TechnicianDto;
import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TechnicianDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void technicianWithTooLongFirstName_shouldNotBeValid() {
        String firstName = "a".repeat(128);
        TechnicianDto user = new TechnicianDto(1, firstName, "Test last name",
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
    public void technicianWithTooLongLastName_shouldNotBeValid() {
        String lastName = "a".repeat(128);
        TechnicianDto user = new TechnicianDto(1, "Test first name", lastName,
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
    public void technicianWithInvalidEmail_shouldNotBeValid() {
        TechnicianDto user = new TechnicianDto(1, "Test first name", "Test last name",
                "Test", "Test phone number", (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "email",
                "Email"
        );

        Assertions.assertTrue(constraintExists, "Email must be valid");
    }

    @Test
    public void technicianWithTooLongEmail_shouldNotBeValid() {
        String email = "a".repeat(128) + "@test.com";
        TechnicianDto user = new TechnicianDto(1, "Test first name", "Test last name",
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
    public void technicianWithTooLongPhoneNumber_shouldNotBeValid() {
        String phone = "1".repeat(32);
        TechnicianDto user = new TechnicianDto(1, "Test first name", "Test last name",
                "Test@test.com", phone, (byte) 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(user),
                "phoneNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Phone number must be <= 31 characters");
    }


    // ---------- rank ----------

    @Test
    public void technicianWithNullRank_shouldNotBeValid() {
        TechnicianDto technician = new TechnicianDto(1, "Test first name", "Test last name",
                "Test@test.com", "Test phone number", null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(technician),
                "rank",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Rank must not be null");
    }

    // ---------- valid case ----------

    @Test
    public void technicianWithValidData_shouldBeValid() {
        TechnicianDto technician = new TechnicianDto(1, "Test first name", "Test last name",
                "Test@test.com", "Test phone number", (byte) 1);

        Assertions.assertTrue(
                validator.validate(technician).isEmpty(),
                "TechnicianDto should be valid"
        );
    }
}
