package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


public record TicketSearchCriteria(
        String keyword,
        Integer clientId,
        Integer versionId,
        Integer softwareId,
        Set<StatusEnum> statuses,
        Set<StatusEnum> statusesExcluded,
        Integer priorityGreaterThan,
        Integer priorityLessThan,
        Integer priorityEquals,
        Integer technicianId,
        LocalDate createdAfter,
        LocalDate createdBefore,
        Boolean unassigned,
        LocalDate closedAfter,
        LocalDate closedBefore
) {
}
