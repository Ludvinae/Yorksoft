type Procedure = {
  idProcedure : number;
  creationDate: Date | null;
  modificationDate: Date | null;
  content: string; // 1 <= size
  idKnowledge: number;
  idAuthor: number | null;
}
