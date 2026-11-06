package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.account.DemoStatusDto;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.DemoCreationLimiter;
import com.mns.cda.suivimns.security.JwtService;
import com.mns.cda.suivimns.service.business.DemoAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Public routes of the demo mode: a visitor gets a trial technician account without signing up.
 * Disabled unless app.demo.enabled=true.
 */
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
@CrossOrigin
public class DemoController {

    private final DemoAccountService demoAccountService;
    private final DemoCreationLimiter demoCreationLimiter;
    private final JwtService jwtService;

    @Operation(summary = "Indique si les comptes d'essai sont disponibles")
    @GetMapping
    public DemoStatusDto getStatus() {
        return new DemoStatusDto(demoAccountService.isEnabled(), demoAccountService.getTtlHours());
    }

    @Operation(summary = "Crée un compte technicien d'essai et renvoie son JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compte d'essai créé, JWT dans le corps"),
            @ApiResponse(responseCode = "404", description = "Mode démo désactivé"),
            @ApiResponse(responseCode = "429", description = "Trop de comptes créés depuis cette IP"),
            @ApiResponse(responseCode = "503", description = "Nombre maximal de comptes d'essai atteint")})
    @PostMapping("/technician")
    public ResponseEntity<String> createTrialTechnician(HttpServletRequest request) {
        if (!demoAccountService.isEnabled()) {
            return ResponseEntity.notFound().build();
        }

        String ip = request.getRemoteAddr();
        if (!demoCreationLimiter.tryAcquire(ip)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header(HttpHeaders.RETRY_AFTER, String.valueOf(demoCreationLimiter.secondsUntilReset(ip)))
                    .build();
        }

        AppUserDetails trialAccount = demoAccountService.createTrialTechnician();
        return new ResponseEntity<>(jwtService.generateToken(trialAccount), HttpStatus.CREATED);
    }
}
