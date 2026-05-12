package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.time.LocalDate;


public record TicketSearchCriteria(
        String keyword,
        Integer clientId,
        Integer versionId,
        Integer softwareId,
        StatusEnum status,
        StatusEnum statusExcluded,
        Integer technicianId,
        LocalDate createdAfter,
        LocalDate createdBefore,
        Boolean unassigned
) {
}
