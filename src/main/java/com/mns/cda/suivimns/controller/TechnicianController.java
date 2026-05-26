package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.TechnicianDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.dto.flat.TechnicianWorkloadDetailedDto;
import com.mns.cda.suivimns.dto.flat.TechnicianWorkloadDetailedDto;
import com.mns.cda.suivimns.security.*;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.entity.TechnicianService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/technician")
@Tag(name="Technicien", description = "Gère les techniciens")
public class TechnicianController {

    protected final TechnicianService technicianService;


    @Operation(summary = "Récupère toutes les techniciens",
            description = "Récupère la liste complète de technicien de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list")
    @IsDirector
    public List<TechnicianDto> getAll() {
        return technicianService.findAll();
    }


    @Operation(summary = "Récupère la liste des techniciens avec leur charge de travail",
            description = "Récupère la liste complète de technicien avec le nombre de tickets actifs qui leur sont assignés")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list/workload")
    @IsManager
    public List<TechnicianWorkloadDetailedDto> getAllWorkload() {
        return technicianService.getAllWorkload();
    }

    @Operation(summary = "Récupère une technicien en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Technicien trouvée"),
            @ApiResponse(responseCode = "404", description = "Technicien non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<TechnicianDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(technicianService.findById(id) , HttpStatus.OK);
        } catch (TechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /* Replaced by route in Auth controller
    @Operation(summary = "Crée une nouvelle technicien")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Technicien crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<TechnicianDto> create(@RequestBody @Valid TechnicianDto technician) {
        return new ResponseEntity<>(technicianService.save(technician), HttpStatus.CREATED);
    }

     */


    @Operation(summary = "Efface une technicien selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Technicien effacée"),
            @ApiResponse(responseCode = "404", description = "Technicien non trouvée")})
    @DeleteMapping("/{id}")
    @IsTechnician
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            technicianService.delete(id, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AppUserService.AccountNotOwnedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "Mettre à jour un technicien",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Technicien mis à jour"),
            @ApiResponse(responseCode = "404", description = "Technicien non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    @IsTechnician
    public ResponseEntity<TechnicianDto> update(@PathVariable int id, @RequestBody @Valid TechnicianDto dto,
                                                @AuthenticationPrincipal AppUserDetails userDetails) {

        try {
            TechnicianDto user = technicianService.update(id, dto, userDetails);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (TechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // IMPLEMENTER TEST UNICITE EMAIL !!!
        } catch (AppUserService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (AppUserService.AccountNotOwnedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "Changer le mot de passe d'un technicien",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Technicien non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    @IsTechnician
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto,
                                      @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            technicianService.updatePassword(id, dto, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TechnicianService.BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (AppUserService.AccountNotOwnedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
