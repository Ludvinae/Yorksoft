package com.mns.cda.suivimns.dto.entity;

import com.mns.cda.suivimns.enumerate.ThemeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ThemeDto(
        Integer idTheme,
        @NotBlank @Size(min = 3, max = 127) String designation,
        @NotNull ThemeEnum code,
        String description
) {
}
