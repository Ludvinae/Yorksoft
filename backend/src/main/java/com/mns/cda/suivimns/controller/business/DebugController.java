package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.service.business.DebugService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/debug")
@CrossOrigin
@IsAdmin
@Tag(name = "Debug", description = "Interventions manuelles et contrôle de l'intégrité des données")
public class DebugController {

    private final DebugService debugService;

    @PostMapping("/metrics-refresh/{id}")
    public void metricsRefresh(@PathVariable int id) {
        debugService.refreshMetrics(id);
    }

    @PostMapping("priority-refresh/{id}")
    public void priorityRefresh(@PathVariable int id) {
        debugService.refreshPriority(id);
    }

}
