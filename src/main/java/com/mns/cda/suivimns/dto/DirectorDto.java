package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DirectorDto(
        Integer idAppUser,
        @Size(max = 127) String firstName,
        @Size(max = 127) String lastName,
        @Email(message="Courriel invalide") @Size(max = 127) String email,
        @Size(max = 31) String phoneNumber
) {
}
