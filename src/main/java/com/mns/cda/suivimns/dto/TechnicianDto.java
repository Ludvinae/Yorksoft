package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record TechnicianDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        @Email(message="Courriel invalide") String email,
        String phoneNumber,
        @NotNull Byte rank
) {
}
