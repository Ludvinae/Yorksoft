package com.mns.cda.suivimns.dto.flat;

public record TicketUpdatedDto(
        int idTicket,
        String title,
        String description,
        Integer currentPriority,
        Integer callDuration
) {
}
