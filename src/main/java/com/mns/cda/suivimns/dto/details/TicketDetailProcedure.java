package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record TicketDetailProcedure(
        Integer idProcedure,
        LocalDateTime creationDate,
        LocalDateTime modificationDAte,
        String title,
        String content

) {
}
