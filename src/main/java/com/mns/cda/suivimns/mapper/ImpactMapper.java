package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ImpactDto;
import com.mns.cda.suivimns.model.Impact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImpactMapper {
    ImpactDto toDto(Impact impact);

    List<ImpactDto> toDtoList(List<Impact> impactList);

    Impact toEntity(ImpactDto dto);
}
