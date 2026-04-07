package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TechnicianUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void technicianWithNullRank_shouldNotBeValid() {
        Technician technician = new Technician();
        technician.setRank(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(technician),
                "rank",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Rank must not be null");
    }
}
