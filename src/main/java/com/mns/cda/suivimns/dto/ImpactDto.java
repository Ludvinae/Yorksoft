package com.mns.cda.suivimns.dto;

public record ImpactDto(
        Integer idImpact,
        String designation,
        Byte priorityFactor,
        String description
) {
}
