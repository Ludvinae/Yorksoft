import { ThemeEnum } from './enum/ThemeEnum';

type Ticket = {
  idTicket: number;
  title: string; // 3 <= size <= 63
  description: string; // 1 <= size
  openDate: Date | null;
  closeDate: Date | null;
  modificationDate: Date | null;
  callDuration: number | null;
  initialPriority: PriorityEnum;
  finalPriority: PriorityEnum;
  currentTheme: ThemeEnum;
  idVersion: number | null;
  idClient: number;
  idImpact: number;
  idUrgency: number;
  commentIds: number[];
  assignmentIds: number[];
  themeIds: number[];
  historyIds: number[];
};
