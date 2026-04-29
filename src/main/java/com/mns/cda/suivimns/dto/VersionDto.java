package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VersionDto(
        Integer idVersion,
        @NotBlank String versionNumber,
        LocalDateTime publicationDate,
        @NotNull Integer idVersionType,
        @NotNull Integer idSoftware
) {
}
