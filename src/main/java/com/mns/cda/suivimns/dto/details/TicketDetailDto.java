package com.mns.cda.suivimns.dto.details;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.enumerate.ThemeEnum;

import java.time.LocalDateTime;

public record TicketDetailDto(
        // Titre
        Integer idTicket,
        String title,
        Integer initialPriority,
        Integer currentPriority,
        StatusEnum currentStatus,

        // Sla
        Boolean overdue,
        LocalDateTime predictedDeadline,

        // Client
        Integer idClient,
        String clientFullName,
        String clientEmail,
        String clientPhone,
        Byte importance,

        // Détails techniques
        ThemeEnum currentTheme,
        String softwareName,
        Integer idVersion,
        String versionAbbreviatedLabel,
        LocalDateTime openDate,
        LocalDateTime closeDate,

        // Description
        String description,
        String solution,

        // Affectation
        String currentTechnicianFullName,
        Integer currentTechnicianId,
        String currentManagerFullName,
        Integer currentManagerId,
        LocalDateTime assignmentDate

) {
}
