package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ManagerDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.exception.ManagerNotFoundException;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.IsDirector;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.exception.AccountNotOwnedException;
import com.mns.cda.suivimns.service.entity.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/manager")
@Tag(name="Manager", description = "Gère les managers")
public class ManagerController {

    protected final ManagerService managerService;

    @Operation(summary = "Récupere toutes les managers",
            description = "Récupere la liste complète de manager de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsDirector
    public List<ManagerDto> getAll() {
        return managerService.findAll();
    }


    @Operation(summary = "Récupére une manager en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Manager trouvée"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<ManagerDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(managerService.findById(id) , HttpStatus.OK);
    }

    /* Replaced by route in Auth controller
    @Operation(summary = "Crée une nouvelle manager")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Manager crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ManagerDto> create(@RequestBody @Valid ManagerDto manager) {
        return new ResponseEntity<>(managerService.save(manager), HttpStatus.CREATED);
    }

     */


    @Operation(summary = "Efface une manager selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Manager effacée"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvée")})
    @DeleteMapping("/{id}")
    @IsManager
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        managerService.delete(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Mettre à jour un manager",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager mis à jour"),
            @ApiResponse(responseCode = "403", description = "Impossible d'acceder à la ressource"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé"),})
    @PatchMapping("/{id}")
    @IsManager
    public ResponseEntity<ManagerDto> update(@PathVariable int id, @RequestBody @Valid ManagerDto dto,
                                             @AuthenticationPrincipal AppUserDetails userDetails) {
        ManagerDto user = managerService.update(id, dto, userDetails);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @Operation(summary = "Changer le mot de passe d'un manager",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    @IsManager
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto,
                                      @AuthenticationPrincipal AppUserDetails userDetails) {
        managerService.updatePassword(id, dto, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
