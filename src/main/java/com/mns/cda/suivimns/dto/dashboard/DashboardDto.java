package com.mns.cda.suivimns.dto.dashboard;


public sealed interface DashboardDto
    permits DashboardAdminDto,
            DashboardDirectorDto,
            DashboardManagerDto,
            DashboardTechnicianDto{}
