package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.HistoryDto;
import com.mns.cda.suivimns.exception.HistoryNotFoundException;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.service.entity.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(name="Historique", description="Enregistre l'historique des statuts des tickets")
public class HistoryController {

    protected final HistoryService historyService;
    @Operation(summary = "Récupere toutes les historiques",
            description = "Récupere la liste complète de historique de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsManager
    public List<HistoryDto> getAll() {
        return historyService.findAll();
    }


    @Operation(summary = "Récupére une historique en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Historique trouvée"),
            @ApiResponse(responseCode = "404", description = "Historique non trouvée")})
    @GetMapping("/{id}")
    @IsManager
    public ResponseEntity<HistoryDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(historyService.findById(id) , HttpStatus.OK);
    }


    // Pas de route CREATE, ce sont les actions des utilisateurs qui provoquent un changement de statut
    // et donc créent une nouvelle entrée dans la table History
    /*
    @PostMapping
    public ResponseEntity<History> create(@RequestBody @Validated(OnCreate.class) History history) {
        History historySaved = historyService.save(history);

        return new ResponseEntity<>(historySaved, HttpStatus.CREATED);
    }

     */

    // Pas de route DELETE et PUT, on ne modifie pas l'historique passé
}
