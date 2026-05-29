package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record TicketDetailComment(
        Integer idComment,
        String content,
        LocalDateTime dateSent,
        LocalDateTime lastModification,
        String authorFullName,
        String authorRole,
        Byte authorRank
) {
}
