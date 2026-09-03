package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.ImpactDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ImpactDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void impactWithBlankDesignation_shouldNotBeValid() {
        ImpactDto impact = new ImpactDto(1, "", (byte) 1, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact),
                "designation",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Designation should not be blank");
    }

    @Test
    public void impactWithToolongDesignation_shouldNotBeValid() {
        String designation = "a".repeat(70);
        ImpactDto impact = new ImpactDto(1, designation, (byte) 1, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact),
                "designation",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Designation should be at most 63 characters long");
    }

    @Test
    public void validImpactWithNullPriorityFactor_shouldNotBeValid() {
        ImpactDto impact = new ImpactDto(1, "Test designation", null, "Test description");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(impact),
                "priorityFactor",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Impact priorityFactor should not be blank");
    }

    // ---------- valid case ----------

    @Test
    public void impactWithValidData_shouldBeValid() {
        ImpactDto impact = new ImpactDto(1, "Test designation", (byte) 1, "Test description");

        Assertions.assertTrue(
                validator.validate(impact).isEmpty(),
                "ImpactDto should be valid"
        );
    }
}
