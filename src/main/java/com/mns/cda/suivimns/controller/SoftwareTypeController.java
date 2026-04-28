package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.SoftwareTypeService;
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
@RequestMapping("/software-type")
@Tag(name="Type de logiciel", description = "Gestion des types de logiciels")
public class SoftwareTypeController {

    protected final SoftwareTypeService softwareTypeService;


    @Operation(summary = "Récupère tous les types de logiciels",
                description = "Récupère la liste complète des types de logiciels")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<SoftwareType> findAll() {
        return softwareTypeService.findAll();
    }


    @Operation(
            summary = "Récupérer un type de logiciel par ID",
            description = "Retourne un type de logiciel spécifique avec ses informations principales")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Type trouvé"),
            @ApiResponse(responseCode = "404", description = "Type non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<SoftwareType> findById(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = softwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(softwareType.get(), HttpStatus.OK);
    }


    @Operation(
            summary = "Créer un type de logiciel",
            description = "Crée un nouveau type de logiciel")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Logiciel créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<SoftwareType> create(@RequestBody @Validated(OnCreate.class) SoftwareType typeToInsert) {

        SoftwareType typeSaved = softwareTypeService.save(typeToInsert);

        return new ResponseEntity<>(typeSaved, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Supprimer un type de logiciel",
            description = "Supprime un type de logiciel existant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Type supprimé"),
            @ApiResponse(responseCode = "404", description = "Type non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = softwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        softwareTypeService.delete(softwareType.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Mettre à jour un logiciel",
            description = "Met à jour le champ 'designation'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Logiciel mis à jour"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<SoftwareType> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) SoftwareType typeToUpdate) {
        try {
            SoftwareType softwareTypeSaved = softwareTypeService.update(typeToUpdate, id);
            return new ResponseEntity<>(softwareTypeSaved, HttpStatus.OK);
        } catch (SoftwareTypeService.SoftwareTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
