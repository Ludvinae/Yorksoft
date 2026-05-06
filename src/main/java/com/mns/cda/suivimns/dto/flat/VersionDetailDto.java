package com.mns.cda.suivimns.dto.flat;

import java.time.LocalDate;

public record VersionDetailDto(
        Integer idVersion,
        String versionNumber,
        String versionTypeDesignation,
        String softwareName,
        LocalDate publicationDate
) {}
