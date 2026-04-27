package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ClassificationDto;
import com.mns.cda.suivimns.model.Classification;

import java.util.List;

public interface ClassificationMapper {
    ClassificationDto toDto(Classification classification);

    List<ClassificationDto> toDtoList(List<Classification> classificationList);

    Classification toEntity(ClassificationDto dto);

}
