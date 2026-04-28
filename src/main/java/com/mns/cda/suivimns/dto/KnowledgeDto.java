package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record KnowledgeDto(
        Integer idKnowledge,
        @NotBlank String subject,
        @NotNull Integer idTheme,
        List<Integer> versionIds,
        List<Integer> articleIds
) {
}
