package com.mns.cda.suivimns.dto.dashboard.graphs;

public record SoftwareStatDto(
        String softwareName,
        Integer numberOfTickets,
        String color
) {
}
