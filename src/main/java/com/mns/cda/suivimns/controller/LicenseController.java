package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.LicenseDto;
import com.mns.cda.suivimns.exception.LicenseNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsDirector;
import com.mns.cda.suivimns.service.entity.LicenseService;
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
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/license")
@Tag(name="Licence", description = "Gère les licenses des clients pour les logiciels")
public class LicenseController {

    protected final LicenseService licenseService;
    @Operation(summary = "Récupere toutes les licences",
            description = "Récupere la liste complète de licence de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsDirector
    public List<LicenseDto> getAll() {
        return licenseService.findAll();
    }


    @Operation(summary = "Récupére une licence en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Licence trouvée"),
            @ApiResponse(responseCode = "404", description = "Licence non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<LicenseDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(licenseService.findById(id) , HttpStatus.OK);
        } catch (LicenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle licence")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Licence crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsDirector
    public ResponseEntity<LicenseDto> create(@RequestBody @Valid LicenseDto license) {
        return new ResponseEntity<>(licenseService.save(license), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une licence selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Licence effacée"),
            @ApiResponse(responseCode = "404", description = "Licence non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            licenseService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (LicenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une licence en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'licenseList' d'une licence")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Licence modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Licence non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsDirector
    public ResponseEntity<LicenseDto> update(@PathVariable int id, @RequestBody @Valid LicenseDto licenseToUpdate) {
        try {
            return new ResponseEntity<>(licenseService.update(id, licenseToUpdate), HttpStatus.OK);
        } catch (LicenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
