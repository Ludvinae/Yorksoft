package com.mns.cda.suivimns.dto.entity;

import com.mns.cda.suivimns.enumerate.PriorityEnum;
import com.mns.cda.suivimns.enumerate.ThemeEnum;
import com.mns.cda.suivimns.model.groups.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record TicketDto(
        Integer idTicket,
        @NotBlank @Size(max = 63) String title,
        @NotBlank String description,
        LocalDateTime openDate,
        LocalDateTime closeDate,
        LocalDateTime modificationDate,
        Integer callDuration,
        PriorityEnum initialPriority,
        PriorityEnum currentPriority,
        ThemeEnum currentTheme,
        Integer idVersion,
        @NotNull(groups = {OnCreate.class}) Integer idClient,
        @NotNull(groups = {OnCreate.class}) Integer idImpact,
        @NotNull(groups = {OnCreate.class}) Integer idUrgency,
        List<Integer> commentIds,
        List<Integer> assignmentIds,
        List<Integer> themeIds,
        List<Integer> historyIds
) {
}
