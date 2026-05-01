package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record KnowledgeDto(
        Integer idKnowledge,
        @NotBlank @Size(max = 255) String subject,
        @NotNull Integer idTheme,
        List<Integer> versionIds,
        List<Integer> articleIds
) {
}
