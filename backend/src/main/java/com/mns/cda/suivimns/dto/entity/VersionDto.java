package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VersionDto(
        Integer idVersion,
        @NotBlank @Size(max = 63) String versionNumber,
        LocalDate publicationDate,
        @NotNull Integer idVersionType,
        @NotNull Integer idSoftware
) {
}
