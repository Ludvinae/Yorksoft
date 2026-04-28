package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iAppUserService;
import com.mns.cda.suivimns.service.inter.iImpactService;
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
@RequiredArgsConstructor
@RequestMapping("/impact")
@Tag(name = "Impact", description = "Gestion des niveaux d'impact sur la priorité des tickets")
public class ImpactController {

    protected final iImpactService impactService;


    @Operation(
            summary = "Récupérer tous les impacts",
            description = "Retourne la liste complète des niveaux d'impact")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list")
    public List<Impact> getAll() {
        return impactService.findAll();
    }


    @Operation(
            summary = "Récupérer un impact par ID",
            description = "Retourne un niveau d'impact à partir de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Impact trouvé"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Impact> getById(@PathVariable int id) {

        Optional<Impact> impact = impactService.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }


    @Operation(
            summary = "Créer un impact",
            description = "Crée un nouveau niveau d'impact")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Impact créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Impact> create(@RequestBody @Validated(OnCreate.class) Impact impact) {
        Impact impactSaved = impactService.save(impact);

        return new ResponseEntity<>(impactSaved, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Supprimer un impact",
            description = "Supprime un niveau d'impact à partir de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Impact supprimé"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Impact> impact = impactService.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        impactService.delete(impact.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Mettre à jour un impact",
            description = "Met à jour les informations d’un impact existant ('designation', 'description', 'priorityFactor')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Impact mis à jour"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Impact> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Impact impactToUpdate) {
        try {
            Impact impactSaved = impactService.update(impactToUpdate, id);
            return new ResponseEntity<>(impactSaved, HttpStatus.OK);
        } catch (iImpactService.ImpactNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
