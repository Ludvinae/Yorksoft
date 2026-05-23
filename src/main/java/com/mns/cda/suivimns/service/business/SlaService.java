package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.model.Ticket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SlaService {

    public Duration resolveSlaDuration(int priorityScore) {

        if (priorityScore <= 25) {
            return Duration.ofHours(72);
        }

        if (priorityScore <= 50) {
            return Duration.ofHours(24);
        }

        if (priorityScore <= 75) {
            return Duration.ofHours(8);
        }

        return Duration.ofHours(2);
    }

    public LocalDateTime computeDeadline(Ticket ticket) {

        return ticket.getOpenDate()
                .plus(resolveSlaDuration(ticket.getCurrentPriority()));
    }

    public boolean isOverdue(Ticket ticket) {

        return LocalDateTime.now()
                .isAfter(computeDeadline(ticket));
    }
}
