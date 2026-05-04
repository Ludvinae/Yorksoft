package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.dto.flat.VersionListDto;
import com.mns.cda.suivimns.service.VersionService;
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


    @Operation(summary = "Récupérer toutes les versions avec les informations de type de version et de logiciel")
    @ApiResponse(responseCode = "200", description = "Liste des versions récupérée")
    @GetMapping("/list/detail")
    public List<VersionListDto> findAllDetail() {
        return versionService.findAllDetail();
    }


    @Operation(summary = "Récupérer une version par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version trouvée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<VersionDto> findById(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(versionService.findById(id) , HttpStatus.OK);
        } catch (VersionService.VersionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        try {
            versionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (VersionService.VersionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            summary = "Mettre à jour une version",
            description = "Modifie les champs 'designation', 'description' et 'priorityFactor'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version mise à jour"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<VersionDto> update(@PathVariable Integer id, @RequestBody @Valid VersionDto versionToUpdate) {
        try {
            return new ResponseEntity<>(versionService.update(id, versionToUpdate), HttpStatus.OK);
        } catch (VersionService.VersionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
