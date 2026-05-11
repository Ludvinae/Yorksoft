package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketClosingService {

    public static class TicketNotEditableException extends RuntimeException {}
    public static class TicketAlreadyClosedException extends RuntimeException {}

    private final StatusTransition transition;
    private final TicketStatusService statusService;

    public static boolean isNotEditable(Ticket ticket) {
        return (ticket.getCurrentStatus() == StatusEnum.CLOSED);
    }

    @Transactional
    public Ticket closeTicket(Ticket ticket, AppUser user, String closingReason) {
        if (isNotEditable(ticket)) {
            throw new TicketAlreadyClosedException();
        }

        if (!transition.canTransition(ticket.getCurrentStatus(), StatusEnum.CLOSED)) {
            throw new StatusTransition.IllegalStatusTransitionException();
        }

        statusService.changeStatus(ticket, StatusEnum.CLOSED, user, closingReason);
        ticket.setCloseDate(LocalDateTime.now());

        return ticket;
    }
}
