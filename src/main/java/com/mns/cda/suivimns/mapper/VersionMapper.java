package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.model.Version;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VersionTypeMapper.class, SoftwareMapper.class})
public interface VersionMapper {
    VersionDto toDto(Version version);

    List<VersionDto> toDtoList(List<Version> versions);

    Version toEntity(VersionDto dto);
}
