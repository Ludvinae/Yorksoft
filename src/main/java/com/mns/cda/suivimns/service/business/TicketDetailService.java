package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.details.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketDetailService {

    private final TicketDao ticketDao;

    public TicketDetailFullDto getTicketDetails(Integer idTicket) {
        TicketDetailDto details = ticketDao.ticketDetail(idTicket);
        TicketDetailKnowledge knowledge = ticketDao.ticketKnowledge(details.currentTheme(), details.idVersion());
        List<TicketDetailArticle> articles;
        if (knowledge != null) {
             articles = ticketDao.ticketDetailArticles(knowledge.idKnowledge());
        } else {
            articles = List.of();
        }

        List<TicketDetailComment> comments = ticketDao.ticketDetailComments(idTicket);

        return new  TicketDetailFullDto(details, comments, knowledge, articles);
    }
}
