type DashboardManagerDto = {
  openTickets: number;
  inProgressTickets: number;
  waitingTickets: number;

  priorityTickets: number;
  overdueTickets: number;
  unassignedTickets: number;

  averageResolutionTime: number;
  averageResponseTime: number;
  averageCallDuration: number;
  ticketPerTechnician: number;
  closedPerDay: number;
  closedPerWeek: number;

  techniciansWorkload: List<TechnicianWorkloadDto>;
  ticketsByStatus: List<TicketStatusStatDto>;
  ticketsBySoftware : List<TicketSoftwareStatDto>;
  ticketsByTheme: List<TicketThemeStatDto>;
};
