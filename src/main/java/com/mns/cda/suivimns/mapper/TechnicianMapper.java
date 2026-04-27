package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.TechnicianDto;
import com.mns.cda.suivimns.model.Technician;

import java.util.List;

public interface TechnicianMapper {
    TechnicianDto toDto(Technician technician);

    List<TechnicianDto> toDtoList(List<Technician> technicianList);

    Technician toEntity(TechnicianDto dto);
}
