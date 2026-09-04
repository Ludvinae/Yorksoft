package com.mns.cda.suivimns.dto.details;

import java.util.List;

public record TicketDetailKnowledge(
        Integer idKnowledge,
        String subject,
        String description,
        String resolution,

        List<TicketDetailProcedure> procedures
) {
    public TicketDetailKnowledge(Integer idKnowledge, String subject, String description, String resolution) {
        this(idKnowledge, subject, description, resolution, List.of());
    }
}
