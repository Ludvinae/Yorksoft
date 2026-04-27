package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.KnowledgeDto;
import com.mns.cda.suivimns.model.Knowledge;

import java.util.List;

public interface KnowledgeMapper {
    KnowledgeDto toDto(Knowledge knowledge);

    List<KnowledgeDto> toDtoList(List<Knowledge> knowledgeList);

    Knowledge toEntity(KnowledgeDto dto);
}
