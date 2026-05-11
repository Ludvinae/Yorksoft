package com.mns.cda.suivimns.dto.workflow;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketProgressDto(
        @NotNull Integer idTechnician,
        @Size(max=255) String statusReason
) {
}
