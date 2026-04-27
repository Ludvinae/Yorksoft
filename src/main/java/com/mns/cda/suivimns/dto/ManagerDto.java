package com.mns.cda.suivimns.dto;

public record ManagerDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
