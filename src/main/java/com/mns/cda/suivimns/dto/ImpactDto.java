package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImpactDto(
        Integer idImpact,
        @NotBlank String designation,
        @NotNull Byte priorityFactor,
        String description
) {
}
