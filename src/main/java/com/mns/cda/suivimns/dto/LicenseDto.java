package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LicenseDto(
        Integer idLicense,
        @NotBlank String licenseNumber,
        LocalDate expirationDate,
        @NotNull Integer idSoftware,
        Integer idClient
) {
}
