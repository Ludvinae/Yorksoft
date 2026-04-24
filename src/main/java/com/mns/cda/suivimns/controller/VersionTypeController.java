package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.VersionTypeResponseDto;
import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/version-type")
@RequiredArgsConstructor
@Tag(name = "Type de version", description = "Gestion des types de version de logiciel")
public class VersionTypeController {

    private final iVersionTypeService versionTypeService;

    @Operation(summary = "Récupérer tous les types de version")
    @ApiResponse(responseCode = "200", description = "Liste des types de version récupérée avec succès")
    @GetMapping("/list")
    public List<VersionTypeResponseDto> getAll() {
        return versionTypeService.findAll();
    }

    @Operation(summary = "Récupérer un type de version par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de version trouvé"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<VersionType> getById(@PathVariable Integer id) {
        Optional<VersionType> versionType = versionTypeService.findById(id);

        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(versionType.get() , HttpStatus.OK);
    }

    @Operation(summary = "Créer un nouveau type de version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Type de version créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<VersionType> create(@RequestBody @Validated() VersionType versionType) {
        VersionType typeSaved = versionTypeService.save(versionType);
        return new ResponseEntity<>(typeSaved , HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un type de version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Type de version supprimé"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<VersionType> versionType = versionTypeService.findById(id);
        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionTypeService.delete(versionType.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Mettre à jour un type de version",
            description = "Met à jour les champs 'designation' et 'urgencyMalus'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type de version mis à jour"),
            @ApiResponse(responseCode = "404", description = "Type de version non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VersionType> update(@PathVariable Integer id, @RequestBody @Validated() VersionType typeToUpdate) {
        try {
            VersionType typeSaved = versionTypeService.update(typeToUpdate, id);
            return new ResponseEntity<>(typeSaved, HttpStatus.OK);
        } catch (iVersionTypeService.VersionTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
