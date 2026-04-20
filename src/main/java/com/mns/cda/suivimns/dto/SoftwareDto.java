package com.mns.cda.suivimns.dto;

import com.mns.cda.suivimns.model.SoftwareType;

public record SoftwareDto(
        String name,
        String description,
        SoftwareType type
) {
}
