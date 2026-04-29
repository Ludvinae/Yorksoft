package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record SoftwareDto(
        Integer idSoftware,
        @NotBlank String name,
        String description,
        Integer idSoftwareType
) {
}
