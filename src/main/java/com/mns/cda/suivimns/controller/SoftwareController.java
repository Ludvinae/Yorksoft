package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iSoftwareService;
import com.mns.cda.suivimns.view.SoftwareVersionListView;
import com.mns.cda.suivimns.view.SoftwareView;
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
@RequestMapping("/software")
@RequiredArgsConstructor
@Tag(name = "Logiciel", description = "Gestion des logiciels")
public class SoftwareController {


    protected final iSoftwareService softwareService;

    @Operation(
            summary = "Récupérer tous les logiciels",
            description = "Retourne la liste complète des logiciels")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list")
    @JsonView(SoftwareView.class)
    public List<Software> getAll() {
        return softwareService.findAll();
    }

    @Operation(
            summary = "Récupérer un logiciel par ID",
            description = "Retourne un logiciel spécifique avec ses informations principales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logiciel trouvé"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvé")})
    @GetMapping("/{id}")
    @JsonView(SoftwareView.class)
    public ResponseEntity<Software> getById(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Récupérer les versions d’un logiciel",
            description = "Retourne un logiciel avec la liste de ses versions")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Versions récupérées"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvé")})
    @GetMapping("/{id}/version/list")
    @JsonView(SoftwareVersionListView.class)
    public ResponseEntity<Software> getSoftwareVersionById(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @Operation(
            summary = "Créer un logiciel",
            description = "Crée un nouveau logiciel à partir d’un DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Logiciel créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Software> create(@RequestBody @Validated(OnCreate.class) SoftwareDto software) {

        Software savedSoftware = softwareService.createSoftware(software);

        return new ResponseEntity<>(savedSoftware, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Supprimer un logiciel",
            description = "Supprime un logiciel existant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logiciel supprimé"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);
        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        softwareService.delete(software.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Mettre à jour un logiciel",
            description = "Met à jour les informations d’un logiciel ('name', 'description', 'softwareType')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logiciel mis à jour"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Software> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Software softwareToUpdate) {
        try {
            Software softwareSaved = softwareService.update(softwareToUpdate, id);
            return new ResponseEntity<>(softwareSaved, HttpStatus.OK);
        } catch (iSoftwareService.SoftwareNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
