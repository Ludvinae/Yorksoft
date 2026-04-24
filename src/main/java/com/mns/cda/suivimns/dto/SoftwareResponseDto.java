package com.mns.cda.suivimns.dto;

public record SoftwareResponseDto(
        int idSoftware,
        String name,
        String description,
        Integer idSoftwareType
) {
}
