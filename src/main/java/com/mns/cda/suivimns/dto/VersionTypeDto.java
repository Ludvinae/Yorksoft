package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record VersionTypeDto(
        Integer idVersionType,
        @NotBlank String designation,
        Byte urgencyMalus
) {
}
