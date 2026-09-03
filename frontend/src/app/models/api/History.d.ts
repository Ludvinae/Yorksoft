type History = {
  idHistory: number;
  startDate: Date | null;
  endDate: Date | null;
  statusReason: string | null; // size <= 255
  idTicket: number;
  idStatus: number;
  idActor: number | null;
}
