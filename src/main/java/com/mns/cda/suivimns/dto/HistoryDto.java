package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record HistoryDto(
        Integer idHistory,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer idTicket,
        Integer idStatus,
        Integer idActor
) {
}
