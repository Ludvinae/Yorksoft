package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TechnicianDto(
        Integer idAppUser,
        @Size(max = 127) String firstName,
        @Size(max = 127) String lastName,
        @NotBlank @Email(message="Courriel invalide") @Size(max = 127) String email, // Unique
        @Size(max = 31) String phoneNumber,
        @NotNull Byte rank
) {
}
