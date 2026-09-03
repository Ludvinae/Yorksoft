package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.LicenseDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LicenseDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void licenseWithTooLongNumber_shouldNotBeValid() {
        String number = "a".repeat(128);
        LicenseDto license = new LicenseDto(1, number, LocalDate.now(), 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "licenseNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "License number must be at most 127 charatcers long");
    }

    @Test
    public void licenseWithBlankNumber_shouldNotBeValid() {
        LicenseDto license = new LicenseDto(1, "", LocalDate.now(), 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "licenseNumber",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "License number must not be blank");
    }

    @Test
    public void validLicenseWithBlankSoftware_shouldNotBeValid() {
        LicenseDto license = new LicenseDto(1, "Test number", LocalDate.now(), null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "idSoftware",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "License should have a software associated");
    }

    // ---------- valid case ----------

    @Test
    public void themeWithValidData_shouldBeValid() {
        LicenseDto license = new LicenseDto(1, "Test number", LocalDate.now(), 1, 1);

        Assertions.assertTrue(
                validator.validate(license).isEmpty(),
                "LicenseDto should be valid"
        );
    }
}
