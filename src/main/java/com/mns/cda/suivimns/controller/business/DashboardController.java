package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.dashboard.DashboardDto;
import com.mns.cda.suivimns.security.IsEmployee;
import com.mns.cda.suivimns.service.business.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "Récupère les statistiques pour le dashboard",
                description = "Si le nombre de jours a observer n'est pas précisé, " +
                        "utilise un valeur par défaut de 30 jours")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Dashboard reçu"),
                    @ApiResponse(responseCode = "401", description = "Non autorisé à accéder à cette ressource")})
    @GetMapping("/dashboard/{timeframeInDays}")
    @IsEmployee
    public ResponseEntity<DashboardDto> getStats(@PathVariable Integer timeframeInDays) {
        try {
            return new ResponseEntity<>(dashboardService.getStats(timeframeInDays), HttpStatus.OK);
        }
        catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
