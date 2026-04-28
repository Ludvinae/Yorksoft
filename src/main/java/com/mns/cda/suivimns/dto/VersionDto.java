package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record VersionDto(
        Integer idVersion,
        String versionNumber,
        LocalDateTime publicationDate,
        Integer idVersionType,
        Integer idSoftware
) {
}
