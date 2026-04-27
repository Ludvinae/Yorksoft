package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ArticleDto;
import com.mns.cda.suivimns.dto.AssignmentDto;
import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.Assignment;

import java.util.List;

public interface AssignmentMapper {
    AssignmentDto toDto(Assignment assignment);

    List<AssignmentDto> toDtoList(List<Assignment> assignmentList);

    Assignment toEntity(AssignmentDto dto);
}
