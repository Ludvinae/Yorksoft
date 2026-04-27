package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record UrgencyDto(
        Integer idUrgency,
        @NotBlank String designation,
        Byte priorityFactor,
        String description
) {
}
