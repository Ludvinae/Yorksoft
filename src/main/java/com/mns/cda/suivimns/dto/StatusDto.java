package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

public record StatusDto(
        Integer idStatus,
        @NotBlank String designation,
        Byte displayOrder
) {
}
