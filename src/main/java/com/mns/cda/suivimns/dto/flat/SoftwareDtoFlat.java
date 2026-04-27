package com.mns.cda.suivimns.dto.flat;

import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.Version;

import java.util.List;

public record SoftwareDtoFlat(
        String name,
        String description,
        SoftwareType type,
        List<Version> versionList
) {
}
