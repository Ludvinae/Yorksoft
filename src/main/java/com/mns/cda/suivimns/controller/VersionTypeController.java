package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.VersionTypeDto;
import com.mns.cda.suivimns.service.entity.VersionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/version-type")
@RequiredArgsConstructor
@Tag(name = "Type de version", description = "Gestion des types de version de logiciel")
public class VersionTypeController {

    private final VersionTypeService versionTypeService;


    @Operation(summary = "Récupérer tous les types de version")
    @ApiResponse(responseCode = "200", description = "Liste des types de version récupérée avec succès")
    @GetMapping("/list")
    public List<VersionTypeDto> getAll() {

        return versionTypeService.findAll();
    }


    @Operation(summary = "Récupérer un type de version par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de version trouvé"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<VersionTypeDto> getById(@PathVariable Integer id) {

        try {
            return new ResponseEntity<>(versionTypeService.findById(id) , HttpStatus.OK);
        } catch (VersionTypeService.VersionTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Créer un nouveau type de version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Type de version créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<VersionTypeDto> create(@RequestBody @Valid VersionTypeDto versionType) {

        return new ResponseEntity<>(versionTypeService.save(versionType), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer un type de version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Type de version supprimé"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        try {
            versionTypeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (VersionTypeService.VersionTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Mettre à jour un type de version",
            description = "Met à jour les champs 'designation' et 'urgencyMalus'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de version mis à jour"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VersionTypeDto> update(@PathVariable Integer id, @RequestBody @Valid VersionTypeDto typeToUpdate) {
        try {
            return new ResponseEntity<>(versionTypeService.update(id, typeToUpdate), HttpStatus.OK);
        } catch (VersionTypeService.VersionTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
