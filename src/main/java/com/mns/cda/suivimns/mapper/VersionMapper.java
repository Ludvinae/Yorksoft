package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.VersionCreateDto;
import com.mns.cda.suivimns.dto.VersionResponseDto;
import com.mns.cda.suivimns.model.Version;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VersionTypeMapper.class, SoftwareMapper.class})
public interface VersionMapper {
    VersionResponseDto toDto(Version version);

    List<VersionResponseDto> toDtoList(List<Version> versions);

    Version toEntity(VersionCreateDto dto);
}
