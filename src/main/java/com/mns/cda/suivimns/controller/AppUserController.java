package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iAppUserService;
import com.mns.cda.suivimns.view.TicketView;
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

    protected final iAppUserService iAppUserservice;

    @Operation(summary = "Lister tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping("/list")
    @JsonView(TicketView.class)
    public List<AppUser> getAll() {
        return iAppUserservice.findAll();
    }

    @Operation(summary = "Récupérer un utilisateur par ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
                   @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getById(@PathVariable int id) {

        Optional<AppUser> user = iAppUserservice.findById(id);
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
        iAppUserservice.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<AppUser> user = iAppUserservice.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iAppUserservice.delete(user.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Mettre à jour un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) AppUser userToUpdate) {
        try {
            iAppUserservice.update(userToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iAppUserService.AppUserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
