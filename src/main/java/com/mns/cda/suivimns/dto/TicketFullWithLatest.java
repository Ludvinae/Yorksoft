package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record TicketFullWithLatest(
    int id,
    String title,
    LocalDateTime modificationDate,
    Integer finalPriority,
    String versionNumber,
    String versionTypeDesignation,
    String softwareName,
    String themeDesignation,
    String statusDesignation,
    long commentCount
) {
}
