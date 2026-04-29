package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ClassificationDto(
        @NotNull Integer idTicket,
        @NotNull Integer idTheme,
        LocalDateTime affectationDate
) {
}
