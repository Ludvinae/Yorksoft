type Ticket = {
  id: number;
  title: string;
  description: string;
  openDate: Date;
  closeDate: Date;
  modificationDate: Date;
  callDuration: number;
  initialPriority: number;
  finalPriority: number;
  idVersion: number;
  idClient: number;
  idImpact: number;
  idUrgency: number;
  commentIds: number[];
  assignmentIds: number[];
  themeIds: number[];
  historyIds: number[];
}
