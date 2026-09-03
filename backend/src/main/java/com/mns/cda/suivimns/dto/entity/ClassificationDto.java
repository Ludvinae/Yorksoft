package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ClassificationDto(
        Integer idClassification,
        @NotNull Integer idTicket,
        @NotNull Integer idTheme,
        LocalDateTime affectationDate
) {
}
