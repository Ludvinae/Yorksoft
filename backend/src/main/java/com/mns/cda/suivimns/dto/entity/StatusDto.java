package com.mns.cda.suivimns.dto.entity;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StatusDto(
        Integer idStatus,
        @NotBlank @Size(min = 3, max = 63) String designation,
        @NotNull StatusEnum code,
        Byte displayOrder
) {
}
