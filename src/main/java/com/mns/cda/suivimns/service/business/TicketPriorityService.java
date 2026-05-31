package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.exception.TicketNotEditableException;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mns.cda.suivimns.service.workflow.TicketClosingService.isNotEditable;

@Service
@RequiredArgsConstructor
public class TicketPriorityService {

    private final PriorityCalculator calculator;

    public void recalculateCurrentPriority(Ticket ticket) {
        if (isNotEditable(ticket)) {
            throw new TicketNotEditableException();
        }

        ticket.setCurrentPriority(compute(ticket));
    }

    public void initializePriority(Ticket ticket) {

        if (ticket.getInitialPriority() != null) {
            throw new IllegalStateException("Priority already initialized");
        }

        Integer priority = compute(ticket);
        ticket.setCurrentPriority(priority);
        ticket.setInitialPriority(priority);
    }

    private Integer compute(Ticket ticket) {
        return calculator.computePriority(
                ticket.getImpact().getPriorityFactor(),
                ticket.getUrgency().getPriorityFactor(),
                ticket.getClient().getImportance(),
                ticket.getVersion().getVersionType().getUrgencyMalus()
        );
    }

}
