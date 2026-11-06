package com.mns.cda.suivimns.dto.dashboard;

import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketEvolutionPointDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;

import java.util.List;

/**
 * Technician dashboard: current workload on the tickets assigned to the technician, personal statistics
 * over the selected period ({@code timeframeInDays}) and the data of its two charts.
 * Durations are in seconds.
 */
public record DashboardTechnicianDto(
    int assignedOpenTickets,
    int assignedWaitingTickets,
    int assignedCriticalTickets,
    int assignedOverdueTickets,
    int closedToday,
    int closedThisWeek,

    int timeframeInDays,
    Double meanTimeToSolveTickets,
    Double meanResponseTime,
    Double resolutionRatio,
    int closedInPeriod,
    double closedPerDay,
    double closedPerWeek,

    List<TicketStatusStatDto> statusStats,
    // "day" or "week": the evolution chart switches to weekly points past a month
    String evolutionUnit,
    List<TicketEvolutionPointDto> evolution,

    List<UserActivity> activities
) implements DashboardDto {}
