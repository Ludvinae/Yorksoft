package com.mns.cda.suivimns.dto.workflow;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketWaitDto(
        @NotNull StatusEnum waitingStatus,
        @NotBlank String statusReason
) {
}
