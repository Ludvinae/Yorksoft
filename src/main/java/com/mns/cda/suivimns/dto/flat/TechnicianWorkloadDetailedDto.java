package com.mns.cda.suivimns.dto.flat;

public record TechnicianWorkloadDetailedDto(
        int idTechnician,
        String fullName,
        Byte rank,
        long activeTicketsCount,
        long criticalTicketsCount
) {
}
