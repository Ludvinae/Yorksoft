package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.time.LocalDate;
import java.util.Set;


public record TicketSearchCriteria(
        String keyword,
        Integer clientId,
        Integer versionId,
        Integer softwareId,
        Set<StatusEnum> statuses,
        Set<StatusEnum> statusesExcluded,
        Integer technicianId,
        LocalDate createdAfter,
        LocalDate createdBefore,
        Boolean unassigned
) {
}
