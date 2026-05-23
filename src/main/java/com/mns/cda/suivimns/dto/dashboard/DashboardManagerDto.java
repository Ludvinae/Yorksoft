package com.mns.cda.suivimns.dto.dashboard;

import java.util.List;

public record DashboardManagerDto(
        int openTickets,
        int inProgressTickets,
        int waitingTickets,

        int priorityTickets,
        int overdueTickets,
        int unassignedTickets,

        double averageResolutionTime,
        double averageResponseTime,

        List<TicketStatusStatDto> ticketsByStatus

        /* A ajouter plus tard

        double averageCallDuration,

        double ticketPerTechnician,
        double solvedPerDay,
        double closedPerWeek,


        List<SoftwareStatDto> ticketsBySoftware,
        List<ThemeStatDto> ticketsByThematic,
        List<TicketEvolutionDto> ticketEvolution

         */
) implements DashboardDto {}
