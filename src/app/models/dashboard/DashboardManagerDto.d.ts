type DashboardManagerDto = {
  openTickets: number;
  inProgressTickets: number;
  waitingTickets: number;

  priorityTickets: number;
  overdueTickets: number;
  unassignedTickets: number;

  averageResolutionTime: number;
  averageResponseTime: number;

  ticketsByStatus: List<TicketStatusStatDto>;
}
