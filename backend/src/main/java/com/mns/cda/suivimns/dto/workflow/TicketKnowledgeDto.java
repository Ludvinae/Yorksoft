package com.mns.cda.suivimns.dto.workflow;

import jakarta.validation.constraints.NotNull;

public record TicketKnowledgeDto(
        @NotNull Integer idKnowledge
) {
}
