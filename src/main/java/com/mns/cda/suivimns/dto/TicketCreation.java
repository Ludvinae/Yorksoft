package com.mns.cda.suivimns.dto;

import com.mns.cda.suivimns.model.Ticket;

// Remove initialPriority when computing it from urgeny * impact is implemented
public record TicketCreation(
        String title,
        String description,
        int initialPriority,
        Integer idImpact,
        Integer idUrgency,
        Integer idClient,
        Integer idVersion,
        String themeDesignation,
        Integer idCreator

) {
}
