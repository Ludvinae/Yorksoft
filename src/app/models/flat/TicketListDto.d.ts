
type TicketListDto = {
  idTicket: number;
  title: string;
  description: string;
  openDate: Date;
  modificationDate: Date;
  closeDate: Date;
  predictedDeadline: Date;
  overdue: boolean;
  callDuration: number;
  currentPriority: number;
  currentStatus: StatusEnum;
  currentTheme: ThemeEnum;
  clientFirstName: string;
  clientLastName: string;
  softwareName: string;
  versionNumber: string;
  versionTypeCode: string;
  idTechnician: number;
  idManager: number;
};
