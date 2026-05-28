package com.mns.cda.suivimns.dto.details;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.util.List;
import java.util.Set;

public record TicketDetailFullDto(
        TicketDetailDto details,
        List<TicketDetailComment> comments,
        TicketDetailKnowledge knowledge,
        List<TicketDetailArticle> articles,
        Set<StatusEnum> possibleStatusTransitions
) {
}
