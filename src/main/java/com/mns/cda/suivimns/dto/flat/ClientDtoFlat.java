package com.mns.cda.suivimns.dto.flat;

public record ClientDtoFlat(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Byte importance
) {
}
