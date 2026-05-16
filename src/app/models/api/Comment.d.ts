type Comment = {
  idComment: number;
  content: string; // 1 <= size
  dateSent: Date | null;
  lastModification: Date | null;
  idTicket: number;
  idAuthor: number | null;
}
