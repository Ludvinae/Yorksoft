package com.mns.cda.suivimns.dto;

public record SoftwareDto(
        Integer idSoftware,
        String name,
        String description,
        Integer idSoftwareType
) {
}
