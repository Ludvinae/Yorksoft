package com.mns.cda.suivimns.dto;

public record VersionTypeResponseDto(
        int idVersionType,
        String designation,
        Byte UrgencyMalus
) {
}
