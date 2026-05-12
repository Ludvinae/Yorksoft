package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.model.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TechnicianMapper {
    public abstract TechnicianDto toDto(Technician technician);

    public abstract List<TechnicianDto> toDtoList(List<Technician> technicianList);

    public abstract Technician toEntity(TechnicianDto dto);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(TechnicianDto dto, @MappingTarget Technician entity);
}
