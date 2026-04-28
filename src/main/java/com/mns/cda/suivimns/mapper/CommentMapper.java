package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.CommentDto;
import com.mns.cda.suivimns.model.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> commentList);

    Comment toEntity(CommentDto dto);
}
