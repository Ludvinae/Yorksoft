
type TicketListDto = {
  idTicket: number;
  title: string;
  description: string;
  openDate: Date;
  modificationDate: Date;
  endDate: Date;
  callCuration: number;
  currentPriority: number;
  currentStatus: StatusEnum;
  currentTheme: ThemeEnum;
  clientFirstName: string;
  clientLastName: string;
};
