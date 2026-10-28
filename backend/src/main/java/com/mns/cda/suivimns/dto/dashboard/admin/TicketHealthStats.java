package com.mns.cda.suivimns.dto.dashboard.admin;

/**
 * Admin dashboard "Tickets" KPI row: counts of tickets whose stored data is inconsistent.
 */
public record TicketHealthStats(
        int closedWithoutEndDate,
        int openWithCloseDate,
        long statusMismatch
) {
}
