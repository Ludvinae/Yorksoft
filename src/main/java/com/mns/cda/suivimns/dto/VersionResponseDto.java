package com.mns.cda.suivimns.dto;

import com.mns.cda.suivimns.model.VersionType;

import java.time.LocalDateTime;

public record VersionResponseDto(
        int idVersion,
        String versionNumber,
        LocalDateTime publicationDate,
        VersionType versionType,
        Integer idSoftware
) {
}
