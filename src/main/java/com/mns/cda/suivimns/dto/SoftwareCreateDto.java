package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record SoftwareCreateDto(
        @NotBlank String name,
        String description,
        Integer idSoftwareType
) {
}
