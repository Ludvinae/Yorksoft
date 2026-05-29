package com.mns.cda.suivimns.dto.flat;

import jakarta.validation.constraints.NotBlank;

public record CommentEditDto(
        @NotBlank String content
) {
}
