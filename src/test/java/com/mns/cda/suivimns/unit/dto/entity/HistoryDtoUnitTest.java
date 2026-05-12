package com.mns.cda.suivimns.unit.dto.entity;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.entity.HistoryDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class HistoryDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validHistoryWithNullTicket_shouldNotBeValid() {
        HistoryDto history = new HistoryDto(1, LocalDateTime.now(), null, "Test reason", null, 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(history),
                "idTicket",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "History should have a ticket associated");
    }

    @Test
    public void validHistoryWithNullStatus_shouldNotBeValid() {
        HistoryDto history = new HistoryDto(1, LocalDateTime.now(), null,"Test reason",1, null, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(history),
                "idStatus",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "History should have a status associated");
    }

    @Test
    public void validHistoryWithTooLongReason_shouldNotBeValid() {
        String reason = "a".repeat(256);
        HistoryDto history = new HistoryDto(1, LocalDateTime.now(), null, reason,1, 1, 1);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(history),
                "statusReason",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "StatusReason should be 255 characters long at maximum");

    }

    // ---------- valid case ----------

    @Test
    public void historyWithValidData_shouldBeValid() {
        HistoryDto history = new HistoryDto(1, LocalDateTime.now(), null,"Test reason",1, 1, 1);

        Assertions.assertTrue(
                validator.validate(history).isEmpty(),
                "HistoryDto should be valid"
        );
    }
}
