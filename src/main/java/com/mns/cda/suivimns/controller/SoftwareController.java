package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.dto.flat.SoftwareDetailDto;
import com.mns.cda.suivimns.service.SoftwareService;
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
@RequestMapping("/software")
@RequiredArgsConstructor
@Tag(name = "Logiciel", description = "Gestion des logiciels")
public class SoftwareController {


    protected final SoftwareService softwareService;


    @Operation(summary = "Récupérer toutes les logiciels")
    @ApiResponse(responseCode = "200", description = "Liste des logiciels récupérée")
    @GetMapping("/list")
    public List<SoftwareDto> findAll() {
        return softwareService.findAll();
    }

    @Operation(summary = "Récupérer toutes les logiciels avec les details sur le type de logiciel")
    @ApiResponse(responseCode = "200", description = "Liste des logiciels récupérée")
    @GetMapping("/list/detail")
    public List<SoftwareDetailDto> findAllDetail() {
        return softwareService.findAllDetail();
    }


    @Operation(summary = "Récupérer un logiciel par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logiciel trouvée"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<SoftwareDto> findById(@PathVariable Integer id) {

        try {
            return new ResponseEntity<>(softwareService.findById(id) , HttpStatus.OK);
        } catch (SoftwareService.SoftwareNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Récupérer les details d'un logiciel par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logiciel trouvée"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvée")})
    @GetMapping("/{id}/detail")
    public ResponseEntity<SoftwareDetailDto> findByIdDetail(@PathVariable Integer id) {

        try {
            return new ResponseEntity<>(softwareService.findByIdDetail(id) , HttpStatus.OK);
        } catch (SoftwareService.SoftwareNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Créer un nouveau logiciel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Logiciel créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<SoftwareDto> create(@RequestBody @Valid SoftwareDto software) {
        return new ResponseEntity<>(softwareService.save(software), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer un logiciel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Logiciel supprimée"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        try {
            softwareService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SoftwareService.SoftwareNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            summary = "Mettre à jour un logiciel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logiciel mise à jour"),
            @ApiResponse(responseCode = "404", description = "Logiciel non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<SoftwareDto> update(@PathVariable Integer id, @RequestBody @Valid SoftwareDto softwareToUpdate) {
        try {
            return new ResponseEntity<>(softwareService.update(id, softwareToUpdate), HttpStatus.OK);
        } catch (SoftwareService.SoftwareNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
