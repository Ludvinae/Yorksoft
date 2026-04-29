package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record SoftwareTypeDto(
        Integer idSoftwareType,
        @NotBlank String designation
) {
}
