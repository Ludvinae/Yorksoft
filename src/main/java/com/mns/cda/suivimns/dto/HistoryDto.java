package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record HistoryDto(
        Integer idHistory,
        LocalDateTime startDate,
        LocalDateTime endDate,
        @Size(max=255) String statusReason,
        @NotNull Integer idTicket,
        @NotNull Integer idStatus,
        Integer idActor
) {
}
