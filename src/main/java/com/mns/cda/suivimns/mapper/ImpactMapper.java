package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ImpactDto;
import com.mns.cda.suivimns.model.Impact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ImpactMapper {
    public abstract ImpactDto toDto(Impact impact);

    public abstract List<ImpactDto> toDtoList(List<Impact> impactList);

    public abstract Impact toEntity(ImpactDto dto);

    // Method helper pour Update
    @Mapping(target = "idImpact", ignore = true)
    public abstract void updateEntityFromDto(ImpactDto dto, @MappingTarget Impact entity);
}
