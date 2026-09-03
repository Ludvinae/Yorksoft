package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UrgencyDto(
        Integer idUrgency,
        @NotBlank @Size(max = 63) String designation,
        @NotNull Byte priorityFactor,
        String description
) {
}
