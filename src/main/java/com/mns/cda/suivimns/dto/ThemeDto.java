package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ThemeDto(
        Integer idTheme,
        @NotBlank @Size(min = 3, max = 127) String designation,
        String description
) {
}
