package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.AppUserDto;
import com.mns.cda.suivimns.dto.flat.NewPasswordDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsDirector;
import com.mns.cda.suivimns.service.entity.AppUserService;
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

@Tag(name = "Utilisateur", description = "Gestion des utilisateurs")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {

    protected final AppUserService appUserService;

    @Operation(summary = "Récupere toutes les utilisateurs",
            description = "Récupere la liste complète de utilisateur de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsDirector
    public List<AppUserDto> getAll() {
        return appUserService.findAll();
    }


    @Operation(summary = "Récupére une utilisateur en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<AppUserDto> getById(@PathVariable int id) {

        return new ResponseEntity<>(appUserService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Crée une nouvelle utilisateur")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Utilisateur crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsAdmin
    public ResponseEntity<AppUserDto> create(@RequestBody @Valid AppUserDto appUser) {
        return new ResponseEntity<>(appUserService.save(appUser), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une utilisateur selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Utilisateur effacée"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        appUserService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @Operation(summary = "Mettre à jour un utilisateur",
                description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    @IsAdmin
    public ResponseEntity<AppUserDto> update(@PathVariable int id, @RequestBody @Valid AppUserDto dto,
                                             @AuthenticationPrincipal AppUserDetails principal) {
        AppUserDto user = appUserService.update(id, dto, principal);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    
    @Operation(summary = "Changer son propre mot de passe",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/password")
    public ResponseEntity<Void> patch(@RequestBody PasswordDto dto,
                                      @AuthenticationPrincipal AppUserDetails principal) {
        appUserService.updatePassword(principal, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Changer le mot de passe d'un utilisateur",
                description = "Remplace directement le mot de passe de l'utilisateur ciblé par la valeur " +
                        "du champ 'newPassword', sans vérification de l'ancien mot de passe (réservé aux admins)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    @IsAdmin
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody @Valid NewPasswordDto dto) {
        appUserService.updatePassword(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
