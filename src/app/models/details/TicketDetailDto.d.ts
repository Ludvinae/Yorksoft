import { StatusEnum } from '../enum/StatusEnum';
import { ThemeEnum } from '../enum/ThemeEnum';

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
  currentTechnicianFullName: string;
  currentManagerFullName: string;
  assignmentDate: Date;
}
