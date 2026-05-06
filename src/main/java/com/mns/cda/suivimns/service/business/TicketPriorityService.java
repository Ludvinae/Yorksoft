package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.enumerate.Priority;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketPriorityService {

    private final PriorityCalculator calculator;

    public void recalculateCurrentPriority(Ticket ticket) {
        ticket.setCurrentPriority(compute(ticket));
    }

    public void initializePriority(Ticket ticket) {
        if (ticket.getInitialPriority() != null) {
            throw new IllegalStateException("Priority already initialized");
        }

        Priority priority = compute(ticket);
        ticket.setCurrentPriority(priority);
        ticket.setInitialPriority(priority);
    }

    private Priority compute(Ticket ticket) {
        return calculator.computePriority(
                ticket.getImpact().getPriorityFactor(),
                ticket.getUrgency().getPriorityFactor(),
                ticket.getClient().getImportance(),
                ticket.getVersion().getVersionType().getUrgencyMalus()
        );
    }
}
