package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ArticleDto;
import com.mns.cda.suivimns.model.Article;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleDto toDto(Article article);

    List<ArticleDto> toDtoList(List<Article> articleList);

    Article toEntity(ArticleDto dto);
}
