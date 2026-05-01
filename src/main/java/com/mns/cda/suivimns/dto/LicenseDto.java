package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Verifier que le numéro de license est unique
 * @param idLicense
 * @param licenseNumber
 * @param expirationDate
 * @param idSoftware
 * @param idClient
 */
public record LicenseDto(
        Integer idLicense,
        @NotBlank @Size(max = 127) String licenseNumber,
        LocalDate expirationDate,
        @NotNull Integer idSoftware,
        Integer idClient
) {
}
