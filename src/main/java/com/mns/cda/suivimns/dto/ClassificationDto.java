package com.mns.cda.suivimns.dto;

import java.time.LocalDateTime;

public record ClassificationDto(
        Integer idTicket,
        Integer idTheme,
        LocalDateTime affectationDate
) {
}
