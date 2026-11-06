package com.mns.cda.suivimns.dto.details;

import java.util.List;

/**
 * Full knowledge article as shown on the knowledge base page: content, theme,
 * author, related software versions and attached procedures.
 */
public record KnowledgeArticleDto(
        Integer idKnowledge,
        String subject,
        String description,
        String resolution,
        Integer idTheme,
        String themeName,
        Integer idAuthor,
        String authorName,
        List<String> versions,
        List<KnowledgeArticleProcedure> procedures
) {
}
