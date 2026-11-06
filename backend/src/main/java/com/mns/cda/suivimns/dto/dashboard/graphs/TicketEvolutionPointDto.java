package com.mns.cda.suivimns.dto.dashboard.graphs;

import java.time.LocalDate;

/**
 * One point of the technician's evolution chart: tickets taken in charge (first move to IN_PROGRESS)
 * and tickets closed, for the day or week starting at {@code date}.
 */
public record TicketEvolutionPointDto(
        LocalDate date,
        long takenInCharge,
        long closed
) {
}
