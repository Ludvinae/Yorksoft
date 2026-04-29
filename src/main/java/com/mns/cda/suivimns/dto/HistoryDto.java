package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record HistoryDto(
        Integer idHistory,
        LocalDateTime startDate,
        LocalDateTime endDate,
        @NotNull Integer idTicket,
        @NotNull Integer idStatus,
        Integer idActor
) {
}
