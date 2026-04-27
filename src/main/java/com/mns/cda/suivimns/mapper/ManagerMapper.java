package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.model.Manager;

import java.util.List;

public interface ManagerMapper {
    ManagerDto toDto(Manager manager);

    List<ManagerDto> toDtoList(List<Manager> managerList);

    Manager toEntity(ManagerDto dto);
}
