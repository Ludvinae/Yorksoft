package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.entity.VersionTypeDto;
import com.mns.cda.suivimns.model.VersionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VersionTypeMapper {

    public abstract VersionTypeDto toDto(VersionType type);

    public abstract List<VersionTypeDto> toDtoList(List<VersionType> types);

    public abstract VersionType toEntity(VersionTypeDto dto);

    // Method helper pour Update
    @Mapping(target = "idVersionType", ignore = true)
    public abstract void updateEntityFromDto(VersionTypeDto dto, @MappingTarget VersionType entity);
}
