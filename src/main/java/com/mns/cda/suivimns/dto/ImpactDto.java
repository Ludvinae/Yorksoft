package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ImpactDto(
        Integer idImpact,
        @NotBlank @Size(max = 63) String designation,
        @NotNull Byte priorityFactor,
        String description
) {
}
