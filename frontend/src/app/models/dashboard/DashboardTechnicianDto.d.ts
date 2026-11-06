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

  activities: UserActivity[];
};
