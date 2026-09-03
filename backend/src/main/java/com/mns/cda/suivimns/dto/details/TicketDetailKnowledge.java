package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record TicketDetailKnowledge(
        Integer idKnowledge,
        String subject,
        String description,
        String resolution,

        Integer idProcedure,
        LocalDateTime procedureCreationDate,
        LocalDateTime procedureModificationDAte,
        String procedureTitle,
        String procedureContent
) {
}
