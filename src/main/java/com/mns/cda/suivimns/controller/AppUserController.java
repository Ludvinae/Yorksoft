package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.flat.AppUserDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.service.inter.iAppUserService;
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

@Tag(name = "Utilisateur", description = "Gestion des utilisateurs")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class AppUserController {

    protected final iAppUserService appUserService;

    @Operation(summary = "Lister tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list")
    public List<AppUser> getAll() {
        return appUserService.findAll();
    }

    @Operation(summary = "Récupérer un utilisateur par ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
                   @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getById(@PathVariable int id) {

        Optional<AppUser> user = appUserService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer un utilisateur",
            description = "Permet de créer un utilisateur (technicien, manager, directeur ou client)")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Utilisateur créé"),
                   @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<AppUser> create(@RequestBody @Validated(OnCreate.class) AppUser user) {
        AppUser userSaved = appUserService.save(user);

        return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<AppUser> user = appUserService.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        appUserService.delete(user.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) AppUser userToUpdate) {
        try {
            appUserService.update(userToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iAppUserService.AppUserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (iAppUserService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

     */

    @Operation(summary = "Mettre à jour un utilisateur",
                description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    public ResponseEntity<AppUser> update(@PathVariable int id, @RequestBody @Validated AppUserDto dto) {

        try {
            AppUser user = appUserService.update(dto, id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (iAppUserService.AppUserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (iAppUserService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Changer le mot de passe d'un utilisateur",
                description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                        "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto) {
        try {
            appUserService.updatePassword(id, dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iAppUserService.AppUserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (iAppUserService.BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
