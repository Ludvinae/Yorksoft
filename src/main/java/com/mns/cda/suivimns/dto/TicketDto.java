package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record TicketDto(
        Integer idTicket,
        @NotBlank String title,
        LocalDateTime openDate,
        LocalDateTime closeDate,
        LocalDateTime modificationDate,
        Integer callDuration,
        Integer finalPriority,
        Integer idClient,
        Integer idCurrentStatus,
        Integer idCurrentTheme,
        List<Integer> idCommentList,
        Integer idCurrentTechnician,
        Integer idCurrentManager
) {
}
