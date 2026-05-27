package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ArticleDto(
        Integer idArticle,
        LocalDateTime creationDate,
        LocalDateTime modificationDate,
        @NotBlank String title,
        @NotBlank String content,
        @NotNull Integer idKnowledge,
        Integer idAuthor
) {
}
