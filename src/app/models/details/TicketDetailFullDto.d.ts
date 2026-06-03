
type TicketDetailFullDto = {
  details: TicketDetailDto;
  comments: TicketDetailComment[];
  knowledge: TicketDetailKnowledge;
  procedures: TicketDetailProcedure[];
  possibleStatusTransitions: StatusEnum[];
};
