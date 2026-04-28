package com.mns.cda.suivimns.dto;

import java.util.List;

public record KnowledgeDto(
        Integer idKnowledge,
        String subject,
        Integer idTheme,
        List<Integer> versionIds
) {
}
