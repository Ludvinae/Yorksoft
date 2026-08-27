package com.mns.cda.suivimns.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto (
    @Size(max = 127) String firstName,
    @Size(max = 127) String lastName,
    @NotBlank
    @Email(message="Courriel invalide") @Size(max = 127) String email, // Unique
    @Size(max = 31) String phoneNumber,
    @NotBlank @Size(min = 3, max = 127) String password,
    Byte rank
) {}
