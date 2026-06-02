package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ProcedureDto(
        Integer idProcedure,
        LocalDateTime creationDate,
        LocalDateTime modificationDate,
        @NotBlank String title,
        @NotBlank String content,
        @NotNull Integer idKnowledge
) {
}
