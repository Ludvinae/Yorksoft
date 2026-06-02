package com.mns.cda.suivimns.dto.search;

public record KnowledgeListDto(
        Integer idKnowledge,
        String subject,
        String description,
        String resolution
) {
}
