package com.mns.cda.suivimns.dto.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketWaitDto(
        @NotNull Integer idTechnician,
        @NotNull StatusEnum waitingStatus,
        @NotBlank String statusReason
) {
}
