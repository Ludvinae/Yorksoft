package com.mns.cda.suivimns.dto.search;

import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import java.time.LocalDateTime;

public record TicketListDto(
    Integer idTicket,
    String title,
    String description,
    LocalDateTime openDate,
    LocalDateTime modificationDate,
    LocalDateTime closeDate,
    LocalDateTime predictedDeadline,
    Boolean overdue,
    Integer callDuration,
    Integer currentPriority,
    StatusEnum currentStatus,
    ThemeEnum currentTheme,
    String clientFirstName,
    String clientLastName,
    String softwareName,
    String versionNumber,
    String versionTypeCode,
    Integer idTechnician,
    Integer idManager
) {}
