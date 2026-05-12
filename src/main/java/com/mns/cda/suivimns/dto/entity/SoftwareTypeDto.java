package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SoftwareTypeDto(
        Integer idSoftwareType,
        @NotBlank @Size(min = 3, max = 127) String designation
) {
}
