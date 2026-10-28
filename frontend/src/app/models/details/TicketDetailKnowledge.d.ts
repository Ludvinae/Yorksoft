type TicketDetailKnowledge = {
  idKnowledge: number;
  subject: string;
  description: string;
  resolution: string;

  procedures: TicketDetailProcedure[];
};

type TicketDetailProcedure = {
  idProcedure: number;
  creationDate: Date;
  modificationDate: Date;
  title: string;
  content: string;
};
