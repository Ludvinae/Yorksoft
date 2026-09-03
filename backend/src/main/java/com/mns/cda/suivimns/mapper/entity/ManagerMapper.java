package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.ManagerDto;
import com.mns.cda.suivimns.model.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ManagerMapper {
    public abstract ManagerDto toDto(Manager manager);

    public abstract List<ManagerDto> toDtoList(List<Manager> managerList);

    public abstract Manager toEntity(ManagerDto dto);

    public abstract Manager toNewEntity(NewUserDto dto);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(ManagerDto dto, @MappingTarget Manager entity);
}
