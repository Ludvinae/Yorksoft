package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StatusDto(
        Integer idStatus,
        @NotBlank @Size(min = 3, max = 63) String designation,
        Byte displayOrder
) {
}
