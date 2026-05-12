package com.mns.cda.suivimns.dto.workflow;

// Remove initialPriority when computing it from urgeny * impact is implemented
public record TicketCreationDto(
        String title,
        String description,
        Integer idImpact,
        Integer idUrgency,
        Integer idClient,
        Integer idVersion,
        String themeDesignation,
        Integer idCreator

) {
}
