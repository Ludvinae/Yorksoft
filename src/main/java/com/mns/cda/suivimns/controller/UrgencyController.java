package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.UrgencyDto;
import com.mns.cda.suivimns.exception.UrgencyNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.UrgencyService;
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
@RequestMapping("/urgency")
@RequiredArgsConstructor
@Tag(name = "Urgence", description = "Gestion des niveaux d'urgence des tickets")
public class UrgencyController {

    protected final UrgencyService urgencyService;


    @Operation(summary = "Récupérer toutes les urgences")
    @ApiResponse(responseCode = "200", description = "Liste des urgences récupérée")
    @GetMapping("/list")
    @IsTechnician
    public List<UrgencyDto> findAll() {
        return urgencyService.findAll();
    }


    @Operation(summary = "Récupérer une urgence par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence trouvée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<UrgencyDto> findById(@PathVariable Integer id) {

        try {
            return new ResponseEntity<>(urgencyService.findById(id) , HttpStatus.OK);
        } catch (UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Créer une nouvelle urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Urgence créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsManager
    public ResponseEntity<UrgencyDto> create(@RequestBody @Valid UrgencyDto urgency) {
        return new ResponseEntity<>(urgencyService.save(urgency), HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer une urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Urgence supprimée"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        try {
            urgencyService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            summary = "Mettre à jour une urgence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Urgence mise à jour"),
            @ApiResponse(responseCode = "404", description = "Urgence non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsManager
    public ResponseEntity<UrgencyDto> update(@PathVariable Integer id, @RequestBody @Valid UrgencyDto urgencyToUpdate) {
        try {
            return new ResponseEntity<>(urgencyService.update(id, urgencyToUpdate), HttpStatus.OK);
        } catch (UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
