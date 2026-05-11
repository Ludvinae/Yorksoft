package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mns.cda.suivimns.service.business.TicketClosingService.isNotEditable;

@Service
@RequiredArgsConstructor
public class TicketPriorityService {

    private final PriorityCalculator calculator;
    private final TicketClosingService closingService;

    public void recalculateCurrentPriority(Ticket ticket) {
        if (isNotEditable(ticket)) {
            throw new TicketClosingService.TicketNotEditableException();
        }

        ticket.setCurrentPriority(compute(ticket));
    }

    public void initializePriority(Ticket ticket) {
        if (ticket.getInitialPriority() != null) {
            throw new IllegalStateException("Priority already initialized");
        }

        PriorityEnum priority = compute(ticket);
        ticket.setCurrentPriority(priority);
        ticket.setInitialPriority(priority);
    }

    private PriorityEnum compute(Ticket ticket) {
        return calculator.computePriority(
                ticket.getImpact().getPriorityFactor(),
                ticket.getUrgency().getPriorityFactor(),
                ticket.getClient().getImportance(),
                ticket.getVersion().getVersionType().getUrgencyMalus()
        );
    }
}
