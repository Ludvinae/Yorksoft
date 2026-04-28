package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.SoftwareType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ManagerMapper {
    public abstract ManagerDto toDto(Manager manager);

    public abstract List<ManagerDto> toDtoList(List<Manager> managerList);

    public abstract Manager toEntity(ManagerDto dto);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    public abstract void updateEntityFromDto(ManagerDto dto, @MappingTarget Manager entity);
}
