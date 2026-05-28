package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.details.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TicketDetailService {

    private final TicketDao ticketDao;
    private final StatusTransition transition;

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
        List<StatusEnum> statusList = transition.getAllowedTransitions(details.currentStatus()).stream().toList();

        return new  TicketDetailFullDto(details, comments, knowledge, articles, statusList);
    }
}
