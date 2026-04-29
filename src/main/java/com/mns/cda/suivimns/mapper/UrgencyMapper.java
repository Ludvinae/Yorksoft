package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.model.Urgency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UrgencyMapper {
    public abstract UrgencyDto toDto(Urgency urgency);

    public abstract List<UrgencyDto> toDtoList(List<Urgency> urgencyList);

    public abstract Urgency toEntity(UrgencyDto dto);

    // Method helper pour Update
    @Mapping(target = "idUrgency", ignore = true)
    public abstract void updateEntityFromDto(UrgencyDto dto, @MappingTarget Urgency entity);
}
