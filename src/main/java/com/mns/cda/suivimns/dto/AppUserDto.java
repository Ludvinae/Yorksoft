package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.Email;

public record AppUserDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        @Email String email,
        String phoneNumber
) {
}
