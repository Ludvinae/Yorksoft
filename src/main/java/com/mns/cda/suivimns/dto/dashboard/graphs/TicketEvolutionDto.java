package com.mns.cda.suivimns.dto.dashboard.graphs;

import java.time.LocalDate;

public record TicketEvolutionDto(
        LocalDate date,
        Integer numberOfTickets
) {
}
