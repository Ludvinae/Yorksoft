package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


public record TicketSearchCriteria(
        String keyword,
        Integer hasClient,
        Integer hasVersion,
        Integer hasSoftware,
        Set<StatusEnum> statuses,
        Set<StatusEnum> statusesExcluded,
        Integer priorityGreaterThan,
        Integer priorityLessThan,
        Integer priorityEquals,
        Integer assignedTo,
        LocalDate createdAfter,
        LocalDate createdBefore,
        LocalDate closedAfter,
        LocalDate closedBefore,
        Boolean isNotClosed,
        Boolean isOverdue
) {
}
