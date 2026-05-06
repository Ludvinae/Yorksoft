package com.mns.cda.suivimns.dto.flat;

import com.mns.cda.suivimns.enumerate.Priority;

import java.time.LocalDateTime;

public record TicketFullWithLatest(
    int id,
    String title,
    LocalDateTime modificationDate,
    Priority currentPriority,
    String versionNumber,
    String versionTypeDesignation,
    String softwareName,
    String themeDesignation,
    String statusDesignation,
    long commentCount
) {
}
