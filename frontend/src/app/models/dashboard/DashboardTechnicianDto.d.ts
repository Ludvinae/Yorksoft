// Mirrors backend dto/dashboard/DashboardTechnicianDto. Durations are in seconds.
type DashboardTechnicianDto = {
  // Current workload on the technician's assigned tickets
  assignedOpenTickets: number;
  assignedWaitingTickets: number;
  assignedCriticalTickets: number;
  assignedOverdueTickets: number;
  closedToday: number;
  closedThisWeek: number;

  // Statistics over the selected period
  timeframeInDays: number;
  meanTimeToSolveTickets: number | null;
  meanResponseTime: number | null;
  resolutionRatio: number | null;
  closedInPeriod: number;
  closedPerDay: number;
  closedPerWeek: number;

  statusStats: TicketStatusStatDto[];
  evolutionUnit: 'day' | 'week';
  evolution: TicketEvolutionPoint[];
  // Active tickets by age since opening: [< 1 day, 1-3 days, 3-7 days, 7 days and more]
  activeTicketsByAge: number[];
  slaUnit: 'week' | 'month';
  sla: SlaPoint[];

  activities: UserActivity[];
};
