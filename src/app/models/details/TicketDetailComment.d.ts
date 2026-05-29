type TicketDetailComment = {
  idComment: number;
  content: string;
  dateSent: Date;
  lastModification: Date;
  authorFullName: string;
  authorRole: String;
  authorRank: number | null;
}
