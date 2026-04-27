package com.mns.cda.suivimns.dto;

public record AppUserDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
