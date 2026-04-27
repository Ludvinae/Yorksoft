package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import com.mns.cda.suivimns.model.SoftwareType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoftwareTypeMapper {

    SoftwareTypeDto toDto(SoftwareType type);

    List<SoftwareTypeDto> toDtoList(List<SoftwareType> types);

    SoftwareType toEntity(SoftwareTypeDto dto);
}
