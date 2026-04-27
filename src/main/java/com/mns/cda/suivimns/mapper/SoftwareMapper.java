package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.model.Software;


import java.util.List;

public interface SoftwareMapper {
    SoftwareDto toDto(Software software);

    List<SoftwareDto> toDtoList(List<Software> software);

    Software toEntity(SoftwareDto dto);
}
