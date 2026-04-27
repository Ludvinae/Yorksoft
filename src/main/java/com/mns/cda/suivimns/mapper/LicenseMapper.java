package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.LicenseDto;
import com.mns.cda.suivimns.model.License;

import java.util.List;

public interface LicenseMapper {
    LicenseDto toDto(License license);

    List<LicenseDto> toDtoList(List<License> licenseList);

    License toEntity(LicenseDto dto);
}

