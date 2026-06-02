type DashboardTechnicianDto = {
  assignedOpenTickets: number;
  assignedWaitingTickets: number;
  assignedCriticalTickets: number;
  assignedOverdueTickets: number;
  closedToday: number;
  closedThisWeek: number;

  meanTimeToSolveTickets: number | null;

  activities: List<UserActivity>;
};
