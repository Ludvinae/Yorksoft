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
import com.mns.cda.suivimns.service.workflow.TicketAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketDetailService {

    private final TicketDao ticketDao;
    private final StatusTransition transition;
    private final TicketAssignmentService assignmentService;

    public TicketDetailFullDto getTicketDetails(Integer idTicket, AppUserDetails principal) {
        if (principal.getUserRole() == null) {
            throw new InvalidUserRoleException();
        }
        Ticket requestedTicket = ticketDao.findById(idTicket).orElseThrow(TicketNotFoundException::new);
        if (Objects.equals(principal.getUserRole(), "CLIENT")) {
            if (!Objects.equals(principal.getId(), requestedTicket.getClient().getIdAppUser())) {
                throw new TicketAccessDeniedException();
            }
        }

        TicketDetailDto details = ticketDao.ticketDetail(idTicket);
        TicketDetailKnowledge knowledge = ticketDao.ticketKnowledge(idTicket)
                .map(k -> new TicketDetailKnowledge(
                        k.idKnowledge(), k.subject(), k.description(), k.resolution(),
                        ticketDao.ticketKnowledgeProcedures(idTicket)))
                .orElse(null);


        List<TicketDetailComment> comments = ticketDao.ticketDetailComments(idTicket);
        List<StatusEnum> statusList = transition.getAllowedTransitions(details.currentStatus()).stream().toList();

        boolean canSelfAssign = assignmentService.canSelfAssign(requestedTicket, principal.getId());

        return new TicketDetailFullDto(details, comments, knowledge, statusList, canSelfAssign);
    }
}
