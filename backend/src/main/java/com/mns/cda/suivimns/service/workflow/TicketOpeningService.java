package com.mns.cda.suivimns.service.workflow;

import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.IllegalStatusTransitionException;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.service.entity.ActivityService;
import com.mns.cda.suivimns.service.entity.HistoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketOpeningService {

    private final HistoryService historyService;
    private final ActivityService activityService;

    @Transactional
    public void initializeStatus(Ticket ticket, AppUser user) {

        // Verifier que le ticket n'a pas déja un statut
        if (ticket.getCurrentStatus() != null) {
            throw new IllegalStatusTransitionException();
        }

        ticket.setCurrentStatus(StatusEnum.OPEN);

        activityService.log(user, "A ouvert le ticket #" + ticket.getIdTicket(), ActivityType.TICKET);

        historyService.addHistory(ticket, user, StatusEnum.OPEN, null);
    }
}
