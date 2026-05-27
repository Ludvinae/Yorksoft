package com.mns.cda.suivimns.dto.details;

import java.util.List;

public record TicketDetailFullDto(
        TicketDetailDto details,
        List<TicketDetailComment> comments,
        TicketDetailKnowledge knowledge,
        List<TicketDetailArticle> articles
) {
}
