package com.mns.cda.suivimns.unit.dto;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.dto.TicketDto;
import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDtoUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void ticketWithTooLongTitle_shouldNotBeValid() {
        String title = "a".repeat(64);
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, title, "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                1, 1, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket),
                "title",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Title must be at most 63 characters long");
    }

    @Test
    public void ticketWithBlankTitle_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "", "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                1, 1, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket),
                "title",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Title must not be blank");
    }

    @Test
    public void ticketWithBlankDescription_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "Test title", "", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                1, 1, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket),
                "description",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Description must not be blank");
    }


    @Test
    public void validTicketWithNullImpact_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "Test title", "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                null, 1, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "idImpact",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have an impact associated");
    }

    @Test
    public void validTicketWithNullUrgency_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "Test title", "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                1, null, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "idUrgency",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have an urgency associated");
    }

    @Test
    public void validTicketWithNullClient_shouldNotBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "Test title", "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, null,
                1, 1, list, list, list, list);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "idClient",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have a client associated on creation");
    }

    // ---------- valid case ----------

    @Test
    public void ticketWithValidData_shouldBeValid() {
        List<Integer> list = new ArrayList<>();
        TicketDto ticket = new TicketDto(1, "Test title", "Test description", LocalDateTime.now(),
                null, LocalDateTime.now(), null, PriorityEnum.VERY_HIGH, PriorityEnum.VERY_HIGH, 1, 1,
                1, 1, list, list, list, list);

        Assertions.assertTrue(
                validator.validate(ticket).isEmpty(),
                "TicketDto should be valid"
        );
    }

}
