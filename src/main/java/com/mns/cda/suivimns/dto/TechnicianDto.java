package com.mns.cda.suivimns.dto;

public record TechnicianDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Byte rank
) {
}
