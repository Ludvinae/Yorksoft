package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.model.Manager;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagerMapper {
    ManagerDto toDto(Manager manager);

    List<ManagerDto> toDtoList(List<Manager> managerList);

    Manager toEntity(ManagerDto dto);
}
