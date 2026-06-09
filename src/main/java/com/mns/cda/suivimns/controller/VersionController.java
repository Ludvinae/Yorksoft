package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.VersionDto;
import com.mns.cda.suivimns.dto.flat.VersionDetailDto;
import com.mns.cda.suivimns.dto.flat.VersionSelectDto;
import com.mns.cda.suivimns.exception.VersionNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.VersionService;
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
    @IsTechnician
    public List<VersionDto> findAll() {
        return versionService.findAll();
    }


    @Operation(summary = "Récupérer toutes les versions avec les informations de type de version et de logiciel")
    @ApiResponse(responseCode = "200", description = "Liste des versions récupérées")
    @GetMapping("/list/detail")
    @IsTechnician
    public List<VersionDetailDto> findAllDetail() {
        return versionService.findAllDetail();
    }


    @Operation(summary = "Récupérer toutes les versions pour un logiciel donné")
    @ApiResponse(responseCode = "200", description = "Liste des versions récupérées")
    @GetMapping("/list/{idSoftware}")
    @IsTechnician
    public List<VersionSelectDto> findAllBySoftware(@PathVariable Integer idSoftware) {
        return versionService.findAllBySoftware(idSoftware);
    }


    @Operation(summary = "Récupérer une version par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version trouvée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<VersionDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(versionService.findById(id) , HttpStatus.OK);
    }

    @Operation(summary = "Récupérer une version par son ID avec les données des entités liées")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version trouvée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @GetMapping("/{id}/detail")
    @IsTechnician
    public ResponseEntity<VersionDetailDto> findByIdDetail(@PathVariable Integer id) {
        return new ResponseEntity<>(versionService.findByIdDetail(id) , HttpStatus.OK);
    }


    @Operation(summary = "Créer une nouvelle version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Version créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsManager
    public ResponseEntity<VersionDto> create(@RequestBody @Valid VersionDto version) {
        return new ResponseEntity<>(versionService.save(version), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer une version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Version supprimée"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        versionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Mettre à jour une version",
            description = "Modifie les champs 'designation', 'description' et 'priorityFactor'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Version mise à jour"),
            @ApiResponse(responseCode = "404", description = "Version non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsManager
    public ResponseEntity<VersionDto> update(@PathVariable Integer id, @RequestBody @Valid VersionDto versionToUpdate) {
        return new ResponseEntity<>(versionService.update(id, versionToUpdate), HttpStatus.OK);
    }
}
