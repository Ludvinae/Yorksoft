package com.mns.cda.suivimns.dto.flat;

import com.mns.cda.suivimns.enumerate.Priority;

public record TicketUpdatedDto(
        int idTicket,
        String title,
        String description,
        Priority currentPriority,
        Integer callDuration
) {
}
