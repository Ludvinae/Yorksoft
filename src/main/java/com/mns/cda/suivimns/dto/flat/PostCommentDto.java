package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.NotBlank;

public record PostCommentDto(
        @NotBlank String content,
        Integer idAuthor,
        Integer idTicket
) {
}
