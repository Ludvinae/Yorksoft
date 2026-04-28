package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.KnowledgeDto;
import com.mns.cda.suivimns.model.Knowledge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KnowledgeMapper {
    KnowledgeDto toDto(Knowledge knowledge);

    List<KnowledgeDto> toDtoList(List<Knowledge> knowledgeList);

    Knowledge toEntity(KnowledgeDto dto);
}
