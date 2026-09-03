package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.ProcedureDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ProcedureDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void procedureWithBlankContent_shouldNotBeValid() {
        ProcedureDto procedure = new ProcedureDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "", 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(procedure),
                "content",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Content must not be blank on creation");
    }


    @Test
    public void validProcedureWithNullKnowledge_shouldNotBeValid() {
        ProcedureDto procedure = new ProcedureDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "Test content", null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(procedure),
                "idKnowledge",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Procedure should have a knowledge associated");
    }

    // ---------- valid case ----------

    @Test
    public void procedureWithValidData_shouldBeValid() {
        ProcedureDto procedure = new ProcedureDto(1, LocalDateTime.now(), LocalDateTime.now(),
                "Test title", "Test content", 1);

        Assertions.assertTrue(
                validator.validate(procedure).isEmpty(),
                "ProcedureDto should be valid"
        );
    }
}
