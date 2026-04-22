package com.mns.cda.suivimns.dto;

public record TicketUpdatedDto(
        int idTicket,
        String title,
        String description,
        int finalPriority,
        Integer callDuration
) {
}
