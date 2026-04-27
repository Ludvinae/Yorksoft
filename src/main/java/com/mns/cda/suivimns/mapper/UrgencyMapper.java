package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.model.Urgency;

import java.util.List;

public interface UrgencyMapper {
    UrgencyDto toDto(Urgency urgency);

    List<UrgencyDto> toDtoList(List<Urgency> urgencyList);

    Urgency toEntity(UrgencyDto dto);
}
