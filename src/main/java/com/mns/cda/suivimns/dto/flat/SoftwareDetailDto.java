package com.mns.cda.suivimns.dto.flat;

public record SoftwareDetailDto(
        Integer idSoftware,
        String name,
        String description,
        String softwareTypeDesignation
) {}
