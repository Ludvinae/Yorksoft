package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.VersionService;
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
@RequestMapping("/version")
@RequiredArgsConstructor
@Tag(name = "Version", description = "Gestion des versions de logiciels")
public class VersionController {

    protected final VersionService versionService;


    @Operation(summary = "Récupérer toutes les versions")
    @ApiResponse(responseCode = "200", description = "Liste des versions récupérée")
    @GetMapping("/list")
    public List<VersionDto> findAll() {
        return versionService.findAll();
    }


    @Operation(summary = "Récupérer une version par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version trouvée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<Version> findById(@PathVariable Integer id) {

        Optional<Version> version = versionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(version.get(), HttpStatus.OK);
    }


    @Operation(summary = "Créer une nouvelle version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Version créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<VersionDto> create(@RequestBody @Valid VersionDto version) {
        return new ResponseEntity<>(versionService.save(version), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer une version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Version supprimée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Optional<Version> version = versionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionService.delete(version.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Mettre à jour une version",
            description = "Modifie les champs 'versionNumber', 'publicationDate' et 'versionType'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version mise à jour"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Version> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Version versionToUpdate) {
        try {
            Version versionSaved = versionService.update(versionToUpdate, id);
            return new ResponseEntity<>(versionSaved, HttpStatus.OK);
        } catch (VersionService.VersionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
