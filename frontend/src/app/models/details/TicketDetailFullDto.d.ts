
type TicketDetailFullDto = {
  details: TicketDetailDto;
  comments: TicketDetailComment[];
  knowledge: TicketDetailKnowledge;
  possibleStatusTransitions: StatusEnum[];
  canSelfAssign: boolean;
  // Rank 0/1 technicians only comment tickets they created or are assigned to (decided by the backend)
  canComment: boolean;
};
