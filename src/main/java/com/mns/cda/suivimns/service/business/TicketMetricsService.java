package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.model.Ticket;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketMetricsService {

    private final ActiveTimeService activeTimeService;
    private final SlaService slaService;

    @Transactional
    public void refreshTicketMetrics(Ticket ticket) {
        if (ticket.getCloseDate() != null) {
            return;
        }

        long activeTime = activeTimeService.getActiveTimeInSeconds(
                ticket.getIdTicket(),
                List.of(StatusEnum.OPEN, StatusEnum.ASSIGNED, StatusEnum.IN_PROGRESS));
        ticket.setActiveTimeInSeconds(activeTime);

        LocalDateTime deadline = slaService.computeDeadline(ticket);
        ticket.setSlaDeadline(deadline);

        boolean overdue = slaService.isOverdue(ticket);
        ticket.setOverdue(overdue);
    }

}