package com.mns.cda.suivimns.dto.dashboard;

import com.mns.cda.suivimns.enumerate.StatusEnum;

public record TicketStatusStatDto(
        StatusEnum statusCode,
        Long numberOfTickets,
        Byte statusColorCode
) {
}
