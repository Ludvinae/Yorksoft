package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsDirector;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.entity.DirectorService;
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
@RequiredArgsConstructor
@RequestMapping("/director")
@Tag(name="Directeur", description = "Gère les directeurs")
public class DirectorController {

    protected final DirectorService directorService;


    @Operation(summary = "Récupere toutes les directeurs",
            description = "Récupere la liste complète de directeur de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsDirector
    public List<DirectorDto> getAll() {
        return directorService.findAll();
    }


    @Operation(summary = "Récupére une directeur en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Directeur trouvée"),
            @ApiResponse(responseCode = "404", description = "Directeur non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<DirectorDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(directorService.findById(id) , HttpStatus.OK);
        } catch (DirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* Replaced by route in Auth controller
    @Operation(summary = "Crée une nouvelle directeur")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Directeur crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<DirectorDto> create(@RequestBody @Valid DirectorDto director) {
        return new ResponseEntity<>(directorService.save(director), HttpStatus.CREATED);
    }

     */


    @Operation(summary = "Efface une directeur selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Directeur effacée"),
            @ApiResponse(responseCode = "404", description = "Directeur non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            directorService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Mettre à jour un directeur",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Directeur mis à jour"),
            @ApiResponse(responseCode = "404", description = "Directeur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    @IsAdmin
    public ResponseEntity<DirectorDto> update(@PathVariable int id, @RequestBody @Valid DirectorDto dto) {

        try {
            DirectorDto user = directorService.update(id, dto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // IMPLEMENTER TEST UNICITE EMAIL !!!
        } catch (AppUserService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Changer le mot de passe d'un directeur",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Directeur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    @IsAdmin
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto) {
        try {
            directorService.updatePassword(id, dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DirectorService.BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
