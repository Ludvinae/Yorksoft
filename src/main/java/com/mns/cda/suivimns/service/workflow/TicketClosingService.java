package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.TicketNotEditableException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketClosingService {

    private final TicketStatusService statusService;
    private final ActivityService activityService;

    public static boolean isNotEditable(Ticket ticket) {
        return (ticket.getCurrentStatus() == StatusEnum.CLOSED);
    }

    @Transactional
    public Ticket closeTicket(Ticket ticket, AppUser user, String closingReason) {
        if (isNotEditable(ticket)) {
            throw new TicketNotEditableException();
        }

        Ticket ticketChanged = statusService.changeStatus(ticket, StatusEnum.CLOSED, user, closingReason);
        ticketChanged.setCloseDate(LocalDateTime.now());

        activityService.log(user, "A clos le ticket #" + ticket.getIdTicket(), ActivityType.TICKET);

        return ticketChanged;
    }
}
