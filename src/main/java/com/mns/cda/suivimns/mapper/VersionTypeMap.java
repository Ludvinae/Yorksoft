package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.VersionTypeCreateDto;
import com.mns.cda.suivimns.dto.VersionTypeResponseDto;
import com.mns.cda.suivimns.model.VersionType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VersionTypeMap {

    VersionTypeResponseDto toDto(VersionType type);

    VersionType toEntity(VersionTypeCreateDto dto);
}
