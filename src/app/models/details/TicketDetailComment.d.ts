type TicketDetailComment = {
  idComment: number;
  content: string;
  dateSent: Date;
  lastModification: Date;
  authorFullName: string;
  authorRole: string;
  authorRank: number | null;
}
