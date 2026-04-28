package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record TicketDto(
        Integer idTicket,
        @NotBlank String title,
        String description,
        LocalDateTime openDate,
        LocalDateTime closeDate,
        LocalDateTime modificationDate,
        Integer callDuration,
        Integer initialPriority,
        Integer finalPriority,
        Integer idVersion,
        Integer idClient,
        Integer idImpact,
        Integer idUrgency,
        List<Integer> commentIds,
        List<Integer> assignmentIds,
        List<Integer> themeIds,
        List<Integer> historyIds
) {
}
