package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record TicketFullWithLatest(
    int id,
    String title,
    LocalDateTime openDate,
    LocalDateTime closeDate,
    LocalDateTime modificationDate,
    String description,
    Integer callDuration,
    Integer initialPriority,
    Integer finalPriority,
    String versionNumber,
    String versionTypeDesignation,
    String softwareName,
    Integer clientId,
    String clientFirstName,
    String clientLastName,
    String themeDesignation,
    String statusDesignation,
    String comment,
    String commentAuthorFirstName,
    String commentAuthorLastName,
    Integer technicianId,
    Integer managerId
) {
}
