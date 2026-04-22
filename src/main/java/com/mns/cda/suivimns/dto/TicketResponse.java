package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record TicketResponse(
            int idTicket,
            String title,
            String description,

            LocalDateTime modificationDate,

            int finalPriority,

            String versionNumber,
            String versionTypeDesignation,
            String softwareName,

            String clientFirstName,
            String clientLastName,

            String currentStatus,
            String currentTheme
) {
}
