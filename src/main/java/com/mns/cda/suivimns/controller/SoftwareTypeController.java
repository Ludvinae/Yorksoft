package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.SoftwareTypeDto;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.SoftwareTypeService;
import com.mns.cda.suivimns.service.SoftwareTypeService;
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
@RequiredArgsConstructor
@RequestMapping("/software-type")
@Tag(name="Type de logiciel", description = "Gestion des types de logiciels")
public class SoftwareTypeController {

    protected final SoftwareTypeService softwareTypeService;

    @Operation(summary = "Récupere toutes les type de logiciels",
            description = "Récupere la liste complète de type de logiciel de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<SoftwareTypeDto> getAll() {
        return softwareTypeService.findAll();
    }


    @Operation(summary = "Récupére une type de logiciel en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Type De Logiciel trouvée"),
            @ApiResponse(responseCode = "404", description = "Type De Logiciel non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<SoftwareTypeDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(softwareTypeService.findById(id) , HttpStatus.OK);
        } catch (SoftwareTypeService.SoftwareTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle type de logiciel")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Type De Logiciel crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<SoftwareTypeDto> create(@RequestBody @Valid SoftwareTypeDto softwareType) {
        return new ResponseEntity<>(softwareTypeService.save(softwareType), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une type de logiciel selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Type De Logiciel effacée"),
            @ApiResponse(responseCode = "404", description = "Type De Logiciel non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            softwareTypeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SoftwareTypeService.SoftwareTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une type de logiciel en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'softwareTypeList' d'une type de logiciel")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Type De Logiciel modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Type De Logiciel non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<SoftwareTypeDto> update(@PathVariable int id, @RequestBody @Valid SoftwareTypeDto softwareTypeToUpdate) {
        try {
            return new ResponseEntity<>(softwareTypeService.update(id, softwareTypeToUpdate), HttpStatus.OK);
        } catch (SoftwareTypeService.SoftwareTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
