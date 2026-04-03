package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record TicketFullWithLatest(
    int id,
    LocalDateTime openDate,
    LocalDateTime closeDate,
    LocalDateTime modificationDate,
    String description,
    Integer callDuration,
    Integer initialPriority,
    Integer finalPriority,
    String urgencyDesignation,
    int urgencyPriority,
    String impactDesignation,
    int impactPriority,
    String versionNumber,
    String versionTypeDesignation,
    String softwareName,
    Integer clientId,
    String clientFirstName,
    String clientLastName,
    int clientImportance,
    String themeDesignation,
    int themePriority,
    String statusDesignation
) {
}
