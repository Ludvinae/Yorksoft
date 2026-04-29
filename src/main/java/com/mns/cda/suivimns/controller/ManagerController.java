package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.service.ManagerService;
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
@RequestMapping("/manager")
@Tag(name="Manager", description = "Gère les managers")
public class ManagerController {

    protected final ManagerService managerService;

    @Operation(summary = "Récupere toutes les managers",
            description = "Récupere la liste complète de manager de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<ManagerDto> getAll() {
        return managerService.findAll();
    }


    @Operation(summary = "Récupére une manager en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Manager trouvée"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(managerService.findById(id) , HttpStatus.OK);
        } catch (ManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle manager")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Manager crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ManagerDto> create(@RequestBody @Valid ManagerDto manager) {
        return new ResponseEntity<>(managerService.save(manager), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une manager selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Manager effacée"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            managerService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Mettre à jour un manager",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager mis à jour"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    public ResponseEntity<ManagerDto> update(@PathVariable int id, @RequestBody @Valid ManagerDto dto) {

        try {
            ManagerDto user = managerService.update(id, dto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // IMPLEMENTER TEST UNICITE EMAIL !!!
        } catch (ManagerService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Changer le mot de passe d'un manager",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Manager non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto) {
        try {
            managerService.updatePassword(id, dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ManagerService.BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
