package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.VersionTypeCreateDto;
import com.mns.cda.suivimns.dto.VersionTypeResponseDto;
import com.mns.cda.suivimns.model.VersionType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VersionTypeMapper {

    VersionTypeResponseDto toDto(VersionType type);

    List<VersionTypeResponseDto> toDtoList(List<VersionType> types);

    VersionType toEntity(VersionTypeCreateDto dto);
}
