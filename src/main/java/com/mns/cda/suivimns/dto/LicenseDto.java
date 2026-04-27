package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record LicenseDto(
        Integer idLicense,
        @NotBlank String licenseNumber,
        LocalDate expirationDate,
        Integer userCount,
        Integer idSoftware,
        List<Integer> idClientList
) {
}
