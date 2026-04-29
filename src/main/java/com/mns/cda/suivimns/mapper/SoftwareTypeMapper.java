package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import com.mns.cda.suivimns.model.SoftwareType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SoftwareTypeMapper {

    public abstract SoftwareTypeDto toDto(SoftwareType type);

    public abstract List<SoftwareTypeDto> toDtoList(List<SoftwareType> types);

    public abstract SoftwareType toEntity(SoftwareTypeDto dto);

    // Method helper pour Update
    @Mapping(target = "idSoftwareType", ignore = true)
    public abstract void updateEntityFromDto(SoftwareTypeDto dto, @MappingTarget SoftwareType entity);
}
