package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.History;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HistoryUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validHistoryWithBlankTicket_shouldNotBeValid() {
        History history = new History();
        history.setTicket(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(history),
                "ticket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "History should have a ticket associated");
    }

    @Test
    public void validHistoryWithBlankStatus_shouldNotBeValid() {
        History history = new History();
        history.setStatus(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(history),
                "status",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "History should have a status associated");
    }
}
