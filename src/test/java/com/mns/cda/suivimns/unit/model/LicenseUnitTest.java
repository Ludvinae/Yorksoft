package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.License;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LicenseUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void licenseWithTooLongNumber_shouldNotBeValid() {
        License license = new License();
        license.setLicenseNumber("a".repeat(128));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "licenseNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "License number must be at most 127 charatcers long");
    }

    @Test
    public void licenseWithBlankNumber_shouldNotBeValid() {
        License license = new License();
        license.setLicenseNumber("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "licenseNumber",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "License number must not be blank");
    }

    @Test
    public void validLicenseWithBlankSoftware_shouldNotBeValid() {
        License license = new License();
        license.setSoftware(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(license),
                "software",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "License should have a software associated");
    }
}
