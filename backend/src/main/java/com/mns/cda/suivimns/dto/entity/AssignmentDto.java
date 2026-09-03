package com.mns.cda.suivimns.dto.entity;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AssignmentDto(
        Integer idAssignment,
        LocalDateTime assignmentDate,
        LocalDateTime endDate,
        @NotNull Integer idTicket,
        @NotNull Integer idManager,
        @NotNull Integer idTechnician
) {
}
