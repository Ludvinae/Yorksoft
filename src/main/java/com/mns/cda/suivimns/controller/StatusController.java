package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/status")
@RequiredArgsConstructor
@Tag(name = "Statut", description = "Gestion des statuts des tickets")
public class StatusController {

    protected final StatusService statusService;


    @Operation(summary = "Récupérer tous les statuts")
    @ApiResponse(responseCode = "200", description = "Liste des statuts récupérée")
    @GetMapping("/list")
    public List<Status> getAll() {
        return statusService.findAll();
    }


    @Operation(summary = "Récupérer un statut par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut trouvé"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Status> getById(@PathVariable int id) {

        Optional<Status> status = statusService.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }


    @Operation(summary = "Créer un statut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Statut créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Status> create(@RequestBody @Validated(OnCreate.class) Status status) {
        Status statusSaved = statusService.save(status);

        return new ResponseEntity<>(statusSaved, HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer un statut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Statut supprimé"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Status> status = statusService.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusService.delete(status.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Champs modifiables :
     * designation
     * displayOrder
     */
    @Operation(
            summary = "Mettre à jour un statut",
            description = "Modifie les champs 'designation' et 'displayOrder'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut mis à jour"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Status statusToUpdate) {
        try {
            Status statusSaved = statusService.update(statusToUpdate, id);
            return new ResponseEntity<>(statusSaved, HttpStatus.OK);
        } catch (StatusService.StatusNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
