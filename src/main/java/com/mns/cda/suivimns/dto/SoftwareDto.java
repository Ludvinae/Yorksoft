package com.mns.cda.suivimns.dto;

public record SoftwareDto(
        Integer idSoftware,
        @NotBlank String name,
        String description,
        Integer idSoftwareType
) {
}
