package com.mns.cda.suivimns.unit.dto.flat;

import com.mns.cda.suivimns.dto.workflow.TicketAssignmentDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TicketAssignmentDtoUnitTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should validate a correct TicketAssignmentDto")
    void shouldValidateValidDto() {
        TicketAssignmentDto dto = new TicketAssignmentDto(
                1,
                2,
                "Re-assigned to a specialist technician"
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail when idTechnician is null")
    void shouldFailWhenIdTechnicianIsNull() {
        TicketAssignmentDto dto = new TicketAssignmentDto(
                null,
                2,
                "Reason"
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<TicketAssignmentDto> violation =
                violations.iterator().next();

        assertThat(violation.getPropertyPath().toString())
                .isEqualTo("idTechnician");

    }

    @Test
    @DisplayName("Should fail when idManager is null")
    void shouldFailWhenIdManagerIsNull() {
        TicketAssignmentDto dto = new TicketAssignmentDto(
                1,
                null,
                "Reason"
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<TicketAssignmentDto> violation =
                violations.iterator().next();

        assertThat(violation.getPropertyPath().toString())
                .isEqualTo("idManager");

    }

    @Test
    @DisplayName("Should validate when statusReason is null")
    void shouldValidateWhenStatusReasonIsNull() {
        TicketAssignmentDto dto = new TicketAssignmentDto(
                1,
                2,
                null
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Should fail when statusReason exceeds 255 characters")
    void shouldFailWhenStatusReasonTooLong() {
        System.out.println(Locale.getDefault());
        String longReason = "a".repeat(256);

        TicketAssignmentDto dto = new TicketAssignmentDto(
                1,
                2,
                longReason
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).hasSize(1);

        ConstraintViolation<TicketAssignmentDto> violation =
                violations.iterator().next();

        assertThat(violation.getPropertyPath().toString())
                .isEqualTo("statusReason");

    }

    @Test
    @DisplayName("Should validate when statusReason has exactly 255 characters")
    void shouldValidateWhenStatusReasonHasMaxLength() {
        String reason = "a".repeat(255);

        TicketAssignmentDto dto = new TicketAssignmentDto(
                1,
                2,
                reason
        );

        Set<ConstraintViolation<TicketAssignmentDto>> violations =
                validator.validate(dto);

        assertThat(violations).isEmpty();
    }
}
