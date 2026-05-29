
type TicketDetailDto = {
  // Titre
  idTicket: number;
  title: string;
  initialPriority: number;
  currentPriority: number;
  currentStatus: StatusEnum;

  // Client
  idClient: number;
  clientFullName: string;
  clientEmail: string;
  clientPhone: string;
  importance: number;

  // Details techniques
  currentTheme: ThemeEnum;
  softwareName: string;
  idVersion: number;
  versionAbbreviatedLabel: string;
  openDate: Date;
  closeDate: Date;

  //Description
  description: string;

  // Affectation
  currentTechnicianFullName: string | null;
  currentTechnicianId: number | null;
  currentManagerFullName: string | null;
  currentManagerId: number | null;
  assignmentDate: Date;
}
