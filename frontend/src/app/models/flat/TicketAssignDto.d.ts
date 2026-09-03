type TicketAssignDto = {
  idTicket: number;
  currentPriority: number;
  overdue: boolean;
  predictedDeadline: date;

  idTechnician: number | null;
}
