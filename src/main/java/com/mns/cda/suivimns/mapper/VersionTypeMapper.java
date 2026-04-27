package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.VersionTypeDto;
import com.mns.cda.suivimns.model.VersionType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VersionTypeMapper {

    VersionTypeDto toDto(VersionType type);

    List<VersionTypeDto> toDtoList(List<VersionType> types);

    VersionType toEntity(VersionTypeDto dto);
}
