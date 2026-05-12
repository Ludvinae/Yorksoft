package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AppUserDto(
        Integer idAppUser,
        @Size(max = 127) String firstName,
        @Size(max = 127) String lastName,
        @NotBlank @Email(message="Courriel invalide") @Size(max = 127) String email, // Unique
        @Size(max = 31) String phoneNumber
) {
}
