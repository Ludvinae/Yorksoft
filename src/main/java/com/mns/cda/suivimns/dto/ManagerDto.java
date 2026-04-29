package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.Email;

public record ManagerDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        @Email(message="Courriel invalide") String email,
        String phoneNumber
) {
}
