package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.dashboard.DashboardDto;
import com.mns.cda.suivimns.security.IsEmployee;
import com.mns.cda.suivimns.service.business.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Récupère les statistiques pour le dashboard",
                description = "Si le nombre de jours a observer n'est pas précisé, " +
                        "utilise un valeur par défaut de 30 jours")
    @ApiResponse(responseCode = "200", description = "Statistiques envoyées")
    @GetMapping("/dashboard/{timeframeInDays}")
    @IsEmployee
    public DashboardDto getStats(@PathVariable Integer timeframeInDays) {

        return dashboardService.getStats(timeframeInDays);
    }


}
