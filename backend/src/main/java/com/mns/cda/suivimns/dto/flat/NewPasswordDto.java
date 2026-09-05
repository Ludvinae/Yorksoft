package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewPasswordDto(
        @NotBlank @Size(min=8, max = 16)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,16}$")
        String newPassword
) {
}
