package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.StatusDto;
import com.mns.cda.suivimns.model.Status;

import java.util.List;

public interface StatusMapper {
    StatusDto toDto(Status status);

    List<StatusDto> toDtoList(List<Status> statusList);

    Status toEntity(StatusDto dto);
}
