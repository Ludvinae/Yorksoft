package com.mns.cda.suivimns.dto.dashboard;

public record SoftwareStatDto(
        String softwareName,
        Integer numberOfTickets,
        String color
) {
}
