package com.mns.cda.suivimns.dto.dashboard;

import java.time.LocalDate;

public record TicketEvolutionDto(
        LocalDate date,
        Integer numberOfTickets
) {
}
