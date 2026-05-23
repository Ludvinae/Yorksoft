package com.mns.cda.suivimns.dto.dashboard;

import java.util.List;

public sealed interface DashboardDto
    permits DashboardAdminDto,
            DashboardDirectorDto,
            DashboardManagerDto,
            DashboardTechnicianDto{}
