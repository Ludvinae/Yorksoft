package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record ThemeDto(
        Integer idTheme,
        @NotBlank String designation,
        String description
) {
}
