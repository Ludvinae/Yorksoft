package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SoftwareDto(
        Integer idSoftware,
        @NotBlank @Size(max = 127) String name,
        String description,
        Integer idSoftwareType
) {
}
