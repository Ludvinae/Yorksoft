package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.model.Urgency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UrgencyMapper {
    UrgencyDto toDto(Urgency urgency);

    List<UrgencyDto> toDtoList(List<Urgency> urgencyList);

    Urgency toEntity(UrgencyDto dto);
}
