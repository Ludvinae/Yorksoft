package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.dashboard.DashboardDto;
import com.mns.cda.suivimns.service.business.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardDto getStats() {

        return dashboardService.getStats();
    }


}
