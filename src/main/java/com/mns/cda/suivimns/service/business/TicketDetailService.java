package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.details.*;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.InvalidUserRoleException;
import com.mns.cda.suivimns.exception.TicketAccessDeniedException;
import com.mns.cda.suivimns.exception.TicketNotFoundException;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketDetailService {

    private final TicketDao ticketDao;
    private final StatusTransition transition;

    public TicketDetailFullDto getTicketDetails(Integer idTicket, AppUserDetails principal) {
        if (principal.getUserRole() == null) {
            throw new InvalidUserRoleException();
        }
        if (Objects.equals(principal.getUserRole(), "CLIENT")) {
            Ticket requestedTicket = ticketDao.findById(idTicket).orElseThrow(TicketNotFoundException::new);
            if (principal.getId() != requestedTicket.getClient().getIdAppUser()) {
                throw new TicketAccessDeniedException();
            }
        }

        TicketDetailDto details = ticketDao.ticketDetail(idTicket);
        TicketDetailKnowledge knowledge = ticketDao.ticketKnowledge(idTicket).orElse(null);


        List<TicketDetailComment> comments = ticketDao.ticketDetailComments(idTicket);
        List<StatusEnum> statusList = transition.getAllowedTransitions(details.currentStatus()).stream().toList();

        return new  TicketDetailFullDto(details, comments, knowledge, statusList);
    }
}
