package com.mns.cda.suivimns.dto;

public record ClientDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Byte importance
) {
}
