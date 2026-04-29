package com.mns.cda.suivimns.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CommentDto(
        Integer idComment,
        @NotBlank String content,
        LocalDateTime dateSent,
        LocalDateTime lastModification,
        @NotNull Integer idTicket,
        Integer idAuthor
) {
}
