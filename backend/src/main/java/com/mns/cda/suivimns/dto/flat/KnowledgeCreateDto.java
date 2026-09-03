package com.mns.cda.suivimns.dto.flat;

public record KnowledgeCreateDto(
        Integer idTheme,
        String themeName,

        Integer idVersion,
        String versionName,

        String ticketTitle,
        String ticketDescription
) {
}
