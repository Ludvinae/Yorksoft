package com.mns.cda.suivimns.dto.dashboard;

import java.util.List;

public record DashboardDto(
        int openTickets,
        int inProgressTickets,
        int waitingTickets,

        int priorityTickets,
        int overdueTickets,
        int unassignedTickets

        /* A ajouter plus tard
        double averageResolutionTime,
        double averageResponseTime,
        double averageCallDuration,

        double ticketPerTechnician,
        double solvedPerDay,
        double closedPerWeek,

        List<TicketStatusStatDto> ticketsByStatus,
        List<SoftwareStatDto> ticketsBySoftware,
        List<ThemeStatDto> ticketsByThematic,
        List<TicketEvolutionDto> ticketEvolution

         */
) {
}
