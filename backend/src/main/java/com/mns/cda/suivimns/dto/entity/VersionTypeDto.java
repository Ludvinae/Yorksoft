package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VersionTypeDto(
        Integer idVersionType,
        @NotBlank @Size(max = 127) String designation,
        @NotBlank @Size(max = 3) String code,
        Byte urgencyMalus
) {
}
