package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordDto(
        @NotBlank @Size(min=8, max = 127) String oldPassword,
        @NotBlank @Size(min=8, max = 127) String newPassword
) {
}
