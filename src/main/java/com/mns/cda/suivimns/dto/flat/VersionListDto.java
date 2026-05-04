package com.mns.cda.suivimns.dto.flat;

import java.time.LocalDate;

public record VersionListDto(
        Integer idVersion,
        String versionNumber,
        String versionTypeDesignation,
        String softwareName,
        LocalDate publicationDate
) {}
