package com.mns.cda.suivimns.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewUserDto (
    @Size(max = 127) String firstName,
    @Size(max = 127) String lastName,
    @NotBlank
    @Email(message="Courriel invalide") @Size(max = 127) String email, // Unique
    @Size(max = 31) String phoneNumber,
    @NotBlank @Size(min = 3, max = 127) String password,
    // Optionnel (defaut 1 si absent, voir TechnicianMapper) : pas de valeurs negatives ou nulles
    @Min(1) @Max(127) Byte rank
) {}
