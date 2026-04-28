package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Version;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VersionUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void validVersionWithBlankNumber_shouldBeInvalid() {

        Version version = new Version();
        version.setVersionNumber("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "versionNumber",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Version number field must not be blank");
    }

    @Test
    public void validVersionWithTooLongNumber_shouldBeInvalid() {
        Version version = new Version();
        version.setVersionNumber("a".repeat(64));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "versionNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Version number field must be at maximum 63 characters long");
    }

    @Test
    public void versionWithNullSoftware_shouldNotBeValid() {
        Version version = new Version();
        version.setSoftware(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "software",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Version must be associated with a software");
    }

}
