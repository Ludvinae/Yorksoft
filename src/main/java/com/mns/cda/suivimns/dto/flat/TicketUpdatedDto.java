package com.mns.cda.suivimns.dto.flat;

import com.mns.cda.suivimns.enumerate.PriorityEnum;

public record TicketUpdatedDto(
        int idTicket,
        String title,
        String description,
        PriorityEnum currentPriority,
        Integer callDuration
) {
}
