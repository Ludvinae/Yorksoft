package com.mns.cda.suivimns.dto.dashboard;

public record TicketStatusStatDto(
        String statusName,
        Integer numberOfTickets,
        String color
) {
}
