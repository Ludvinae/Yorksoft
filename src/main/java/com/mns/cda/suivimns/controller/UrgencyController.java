package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.UrgencyDto;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.UrgencyService;
import com.mns.cda.suivimns.service.VersionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Urgence", description = "Gestion des niveaux d'urgence des tickets")
public class UrgencyController {

    protected final UrgencyService urgencyService;


    @Operation(summary = "Récupérer tous les niveaux d'urgence")
    @ApiResponse(responseCode = "200", description = "Liste des urgences récupérée")
    @GetMapping("/list")
    public List<UrgencyDto> getAll() {
        return urgencyService.findAll();
    }


    @Operation(summary = "Récupérer un niveau d'urgence par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence trouvée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<UrgencyDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(urgencyService.findById(id) , HttpStatus.OK);
        } catch (UrgencyService.UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Créer un niveau d'urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Urgence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<UrgencyDto> create(@RequestBody @Valid UrgencyDto urgency) {
        return new ResponseEntity<>(urgencyService.save(urgency), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer un niveau d'urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Urgence supprimée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            urgencyService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UrgencyService.UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            summary = "Mettre à jour un niveau d'urgence",
            description = "Modifie les champs 'designation', 'description' et 'priorityFactor'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence mise à jour"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<UrgencyDto> update(@PathVariable int id, @RequestBody @Valid UrgencyDto urgencyToUpdate) {
        try {
            return new ResponseEntity<>(urgencyService.update(id, urgencyToUpdate), HttpStatus.OK);
        } catch (UrgencyService.UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
