package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.SoftwareCreateDto;
import com.mns.cda.suivimns.dto.SoftwareResponseDto;
import com.mns.cda.suivimns.model.Software;


import java.util.List;

public interface SoftwareMapper {
    SoftwareResponseDto toDto(Software software);

    List<SoftwareResponseDto> toDtoList(List<Software> software);

    Software toEntity(SoftwareCreateDto dto);
}
