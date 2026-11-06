package com.mns.cda.suivimns.dto.dashboard.graphs;

import java.time.LocalDate;

/**
 * Tickets the technician closed in the week or month starting at {@code date}, split by whether they were
 * closed before their SLA deadline. Tickets without a deadline are left out.
 */
public record SlaPointDto(
        LocalDate date,
        long onTime,
        long late
) {
}
