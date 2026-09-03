package com.mns.cda.suivimns.dto.workflow;

// Remove initialPriority when computing it from urgeny * impact is implemented
public record TicketCreationDto(
        String title,
        String description,
        Integer idSoftware,
        Integer idVersion,
        Integer idImpact,
        Integer idUrgency,
        Integer idTheme,
        Integer idClient

) {
}
