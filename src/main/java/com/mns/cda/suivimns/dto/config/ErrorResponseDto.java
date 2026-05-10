package com.mns.cda.suivimns.dto.config;

public record ErrorResponseDto(
        int status,
        String error,
        String message
) {
}
