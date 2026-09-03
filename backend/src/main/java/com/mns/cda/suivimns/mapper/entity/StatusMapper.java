package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.entity.StatusDto;
import com.mns.cda.suivimns.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StatusMapper {
    public abstract StatusDto toDto(Status status);

    public abstract List<StatusDto> toDtoList(List<Status> statusList);

    public abstract Status toEntity(StatusDto dto);

    // Method helper pour Update
    @Mapping(target = "idStatus", ignore = true)
    public abstract void updateEntityFromDto(StatusDto dto, @MappingTarget Status entity);
}
