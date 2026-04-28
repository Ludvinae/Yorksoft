package com.mns.cda.suivimns.unit.model;

import com.mns.cda.suivimns.TestUtils;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TicketUnitTest {

    protected static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void ticketWithTooLongTitle_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setTitle("a".repeat(64));

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket),
                "title",
                "Size"
        );

        Assertions.assertTrue(constraintExists, "Title must be at most 63 characters long");
    }

    @Test
    public void ticketWithBlankTitle_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setTitle("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket),
                "title",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Title must not be blank");
    }

    @Test
    public void ticketWithBlankCreatedDescription_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setDescription("");

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "description",
                "NotBlank"
        );

        Assertions.assertTrue(constraintExists, "Description must not be blank");
    }


    @Test
    public void validTicketWithBlankImpact_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setImpact(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "impact",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have an impact associated");
    }

    @Test
    public void validTicketWithBlankUrgency_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setUrgency(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "urgency",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have an urgency associated");
    }

    @Test
    public void validTicketWithBlankClient_shouldNotBeValid() {
        Ticket ticket = new Ticket();
        ticket.setClient(null);

        boolean constraintExists = TestUtils.constraintViolationExists(
                validator.validate(ticket, OnCreate.class),
                "client",
                "NotNull"
        );

        Assertions.assertTrue(constraintExists, "Ticket should have a client associated on creation");
    }



}
