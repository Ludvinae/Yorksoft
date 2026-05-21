package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.model.License;

import java.util.List;

public record ClientListDto(
        Integer idAppUser,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Byte importance,
        List<Integer> softwareIdsList
) {
}
