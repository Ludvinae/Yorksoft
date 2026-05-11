package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketAssignmentDto(
        @NotNull Integer idTechnician,
        @NotNull Integer idManager,
        @Size(max=255) String statusReason // Used when re-assigning a ticket mostly
) {
}
