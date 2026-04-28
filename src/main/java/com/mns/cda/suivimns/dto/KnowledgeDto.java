package com.mns.cda.suivimns.dto;

public record KnowledgeDto(
        Integer idKnowledge,
        String subject,
        Integer idTheme
        //Integer[] versionList
) {
}
