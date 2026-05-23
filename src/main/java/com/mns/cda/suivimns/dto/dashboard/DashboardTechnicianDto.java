package com.mns.cda.suivimns.dto.dashboard;

public record DashboardTechnicianDto(
    int assignedOpenTickets,
    int assignedWaitingTickets,
    int assignedCriticalTickets,
    int assignedOverdueTickets
) implements DashboardDto {}
