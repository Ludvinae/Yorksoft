package com.mns.cda.suivimns.dto.dashboard;

public record DashboardAdminDto(
        int closedTicketsWithoutEndDate
) implements DashboardDto {
}
