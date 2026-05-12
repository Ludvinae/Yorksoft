package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ClientDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.entity.ClientService;
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
@RequestMapping("/client")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Gestion des clients")
public class ClientController {

    protected final ClientService clientService;

    @Operation(summary = "Récupere toutes les clients",
            description = "Récupere la liste complète de client de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<ClientDto> getAll() {
        return clientService.findAll();
    }


    @Operation(summary = "Récupére une client en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvée"),
            @ApiResponse(responseCode = "404", description = "Client non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(clientService.findById(id) , HttpStatus.OK);
        } catch (ClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle client")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Client crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une client selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Client effacée"),
            @ApiResponse(responseCode = "404", description = "Client non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            clientService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Mettre à jour un client",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client mis à jour"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable int id, @RequestBody @Valid ClientDto dto) {

        try {
            ClientDto user = clientService.update(id, dto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // IMPLEMENTER TEST UNICITE EMAIL !!!
        } catch (AppUserService.EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Changer le mot de passe d'un client",
            description = "Compare le champ 'oldPassword' avec le mot de passe, " +
                    "et si identique le remplace avec la valeur du champ 'newPassword'")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mot de passe mis à jour"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto) {
        try {
            clientService.updatePassword(id, dto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ClientService.BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
