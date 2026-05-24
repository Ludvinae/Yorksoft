package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
