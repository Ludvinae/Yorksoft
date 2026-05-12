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
    LocalDateTime endDate,
    StatusEnum currentStatus,
    ThemeEnum currentTheme,
    Integer idClient,
    Integer idTechnician

) {}
