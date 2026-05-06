package com.mns.cda.suivimns.dto.flat;

import com.mns.cda.suivimns.enumerate.Priority;

import java.time.LocalDateTime;

public record TicketResponse(
            int idTicket,
            String title,
            String description,

            LocalDateTime modificationDate,

            Priority currentPriority,

            String versionNumber,
            String versionTypeDesignation,
            String softwareName,

            String clientFirstName,
            String clientLastName,

            String currentStatus,
            String currentTheme
) {
}
