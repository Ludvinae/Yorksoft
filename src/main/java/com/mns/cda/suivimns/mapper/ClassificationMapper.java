package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ClassificationDto;
import com.mns.cda.suivimns.model.Classification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassificationMapper {
    ClassificationDto toDto(Classification classification);

    List<ClassificationDto> toDtoList(List<Classification> classificationList);

    Classification toEntity(ClassificationDto dto);

}
