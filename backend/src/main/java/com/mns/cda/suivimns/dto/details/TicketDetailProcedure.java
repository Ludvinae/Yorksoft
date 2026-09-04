package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record TicketDetailProcedure(
        Integer idProcedure,
        LocalDateTime creationDate,
        LocalDateTime modificationDate,
        String title,
        String content
) {
}
