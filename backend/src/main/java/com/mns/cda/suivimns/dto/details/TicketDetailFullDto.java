package com.mns.cda.suivimns.dto.details;

import com.mns.cda.suivimns.enumerate.StatusEnum;

import java.util.List;

public record TicketDetailFullDto(
        TicketDetailDto details,
        List<TicketDetailComment> comments,
        TicketDetailKnowledge knowledge,
        List<StatusEnum> possibleStatusTransitions
) {
}
