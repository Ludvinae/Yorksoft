package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record KnowledgeArticleProcedure(
        Integer idProcedure,
        String title,
        String content,
        LocalDateTime creationDate,
        LocalDateTime modificationDate,
        String authorName
) {
}
