package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
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
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/license")
@Tag(name="Licence", description = "Gère les licenses des clients pour les logiciels")
public class LicenseController {

    protected final iLicenseService licenseService;


    @Operation(summary = "Récupère toutes les licences")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<License> getAll() {
        return licenseService.findAll();
    }


    @Operation(summary = "Récupère une licence en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Licence trouvée"),
                    @ApiResponse(responseCode = "404", description = "Licence non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<License> getById(@PathVariable int id) {

        Optional<License> license = licenseService.findById(id);
        if (license.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(license.get(), HttpStatus.OK);
    }


    @Operation(summary="Crée une nouvelle licence")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Licence crée"),
                    @ApiResponse(responseCode = "400", description = "Données non valides")})
    @PostMapping
    public ResponseEntity<License> create(@RequestBody @Validated(OnCreate.class) License license) {
        License licenseSaved = licenseService.save(license);

        return new ResponseEntity<>(licenseSaved, HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une licence en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Licence effacée"),
                    @ApiResponse(responseCode = "404", description = "Licence introuvable")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<License> license = licenseService.findById(id);
        if (license.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        licenseService.delete(license.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifie une licence en fonction de son ID",
                description = "Modifie les champs 'userCount' et 'expirationDate'")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "licence mis à jour"),
                    @ApiResponse(responseCode = "404", description = "Licence non trouvé"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<License> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) License licenseToUpdate){
        try {
            License licenseSaved = licenseService.update(licenseToUpdate, id);
            return new ResponseEntity<>(licenseSaved, HttpStatus.OK);
        } catch (iLicenseService.LicenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
