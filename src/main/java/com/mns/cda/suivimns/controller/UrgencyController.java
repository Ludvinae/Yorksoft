package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iUrgencyService;
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
@RequestMapping("/urgency")
@RequiredArgsConstructor
@Tag(name = "Urgency", description = "Gestion des niveaux d'urgence des tickets")
public class UrgencyController {

    protected final iUrgencyService urgencyService;

    @Operation(summary = "Récupérer tous les niveaux d'urgence")
    @ApiResponse(responseCode = "200", description = "Liste des urgences récupérée")
    @GetMapping("/list")
    public List<Urgency> getAll() {
        return urgencyService.findAll();
    }

    @Operation(summary = "Récupérer un niveau d'urgence par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence trouvée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<Urgency> getById(@PathVariable int id) {

        Optional<Urgency> urgency = urgencyService.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(urgency.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer un niveau d'urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Urgence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Urgency> create(@RequestBody @Validated(OnCreate.class) Urgency urgency) {
        Urgency urgencySaved = urgencyService.save(urgency);

        return new ResponseEntity<>(urgencySaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un niveau d'urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Urgence supprimée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Urgency> urgency = urgencyService.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        urgencyService.delete(urgency.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Mettre à jour un niveau d'urgence",
            description = "Modifie les champs 'designation', 'description' et 'priorityFactor'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence mise à jour"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Urgency> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Urgency urgencyToUpdate) {
        try {
            Urgency urgencySaved = urgencyService.update(urgencyToUpdate, id);
            return new ResponseEntity<>(urgencySaved, HttpStatus.OK);
        } catch (iUrgencyService.UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
