package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record VersionCreateDto(
        @NotBlank String versionNumber,
        @NotNull LocalDateTime publicationDate,
        Integer idVersionType,
        Integer idSoftware
        ) {
}
