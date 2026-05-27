type TicketDetailFullDto = {
  details: TicketDetailDto;
  comments: List<TicketDetailComment>;
  knowledge: TicketDetailKnowledge;
  articles: List<TicketDetailArticle>;
};
