package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record VersionTypeCreateDto(
        @NotBlank String designation,
        Byte UrgencyMalus
) {}
