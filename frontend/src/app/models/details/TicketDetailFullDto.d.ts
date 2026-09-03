
type TicketDetailFullDto = {
  details: TicketDetailDto;
  comments: TicketDetailComment[];
  knowledge: TicketDetailKnowledge;
  possibleStatusTransitions: StatusEnum[];
};
