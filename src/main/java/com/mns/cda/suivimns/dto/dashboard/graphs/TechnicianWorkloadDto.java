package com.mns.cda.suivimns.dto.dashboard.graphs;

public record TechnicianWorkloadDto(
        String technicianFirstName,
        String technicianLastName,
        long numberOfAssignedTickets
) {
}
