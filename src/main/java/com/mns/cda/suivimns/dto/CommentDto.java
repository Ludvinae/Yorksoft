package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDto(
        Integer idComment,
        @NotBlank String content,
        LocalDateTime dateSent,
        LocalDateTime lastModification,
        Integer idTicket,
        Integer idAuthor
) {
}
