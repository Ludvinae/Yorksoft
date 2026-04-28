package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.LicenseDto;
import com.mns.cda.suivimns.model.License;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    LicenseDto toDto(License license);

    List<LicenseDto> toDtoList(List<License> licenseList);

    License toEntity(LicenseDto dto);
}

