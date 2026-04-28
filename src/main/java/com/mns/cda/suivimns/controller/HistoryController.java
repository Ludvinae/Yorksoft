package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(name="Historique", description="Enregistre l'historique des statuts des tickets")
public class HistoryController {

    protected final HistoryService historyService;


    @Operation(summary = "Récupérer tous les historiques")
    @ApiResponse(responseCode = "200", description = "Liste des historiques récupérée avec succès")
    @GetMapping("/list")
    public List<History> getAll() {
        return historyService.findAll();
    }


    @Operation(summary = "Récupérer un historique par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historique trouvé"),
            @ApiResponse(responseCode = "404", description = "Historique non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable int id) {

        Optional<History> history = historyService.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(history.get(), HttpStatus.OK);
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
