package com.mns.cda.suivimns.dto.workflow;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketSolvedDto (
    @NotNull Integer idTechnician,
    @NotBlank @Size(max=255) String statusReason
    ){}
