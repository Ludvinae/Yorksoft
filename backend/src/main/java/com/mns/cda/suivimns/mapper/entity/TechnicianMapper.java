package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.model.Technician;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TechnicianMapper {
    public abstract TechnicianDto toDto(Technician technician);

    public abstract List<TechnicianDto> toDtoList(List<Technician> technicianList);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Technician toEntity(TechnicianDto dto);

    // Rang fourni par le client si present, sinon valeur par defaut (1)
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "rank", source = "rank", defaultValue = "1")
    public abstract Technician toNewEntity(NewUserDto dto);

    // Method helper pour Update
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(TechnicianDto dto, @MappingTarget Technician entity);
}
