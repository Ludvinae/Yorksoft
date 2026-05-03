package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.VersionDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VersionDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Test
    public void validVersionWithBlankNumber_shouldBeInvalid() {
        VersionDto version = new VersionDto(1, "", LocalDate.now(), 1, 1);


        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "versionNumber",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Version number field must not be blank");
    }

    @Test
    public void validVersionWithTooLongNumber_shouldBeInvalid() {
        String versionNumber = "a".repeat(64);
        VersionDto version = new VersionDto(1, versionNumber, LocalDate.now(), 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "versionNumber",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Version number field must be at maximum 63 characters long");
    }

    @Test
    public void versionWithNullSoftware_shouldNotBeValid() {
        VersionDto version = new VersionDto(1, "Test number", LocalDate.now(), 1, null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "idSoftware",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Version must be associated with a software");
    }

    @Test
    public void versionWithNullVersionType_shouldNotBeValid() {
        VersionDto version = new VersionDto(1, "Test number", LocalDate.now(), null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(version),
                "idVersionType",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Version must be associated with a version type");
    }

    // ---------- valid case ----------

    @Test
    public void versionWithValidData_shouldBeValid() {
        VersionDto type = new VersionDto(1, "Test number", LocalDate.now(), 1, 1);

        Assertions.assertTrue(
                validator.validate(type).isEmpty(),
                "VersionDto should be valid"
        );
    }

}
