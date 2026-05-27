package com.mns.cda.suivimns.dto.details;

import java.time.LocalDateTime;

public record TicketDetailArticle(
        Integer idArticle,
        LocalDateTime creationDate,
        LocalDateTime modificationDAte,
        String title,
        String content

) {
}
