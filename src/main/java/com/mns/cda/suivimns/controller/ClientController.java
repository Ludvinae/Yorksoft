package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ClientDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.ClientSearchCriteria;
import com.mns.cda.suivimns.exception.*;
import com.mns.cda.suivimns.security.*;
import com.mns.cda.suivimns.service.entity.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/client")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Gestion des clients")
public class ClientController {

    protected final ClientService clientService;


    @Operation(summary = "Récupere tous les clients",
            description = "Récupere la liste paginée de client de la base")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés"),
            @ApiResponse(responseCode = "400", description = "Critère de recherche invalide")})
    @GetMapping("/list")
    @IsTechnician
    public ResponseEntity<Page<ClientListDto>> search(
            ClientSearchCriteria criteria,
            @PageableDefault(size = 10, sort = "lastName", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        try {
            return new ResponseEntity<>(clientService.search(criteria, pageable) , HttpStatus.OK);
        } catch (InvalidSortCriteriaException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Récupére une client en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Client trouvée"),
            @ApiResponse(responseCode = "404", description = "Client non trouvée")})
    @GetMapping("/{id}")
    @IsDirector
    public ResponseEntity<ClientDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(clientService.findById(id) , HttpStatus.OK);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /* Replaced by route in Auth controller
    @Operation(summary = "Crée une nouvelle client")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Client crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }

     */


    @Operation(summary = "Efface une client selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Client effacée"),
            @ApiResponse(responseCode = "404", description = "Client non trouvée")})
    @DeleteMapping("/{id}")
    @IsClient
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            clientService.delete(id, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AccountNotOwnedException e) {
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "Mettre à jour un client",
            description = "Modifie les champs 'firstName', 'lastName', 'email' et 'phoneNumber'")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client mis à jour"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé"),
            @ApiResponse(responseCode = "400", description = "Email déja utilisé")})
    @PatchMapping("/{id}")
    @IsClient
    public ResponseEntity<ClientDto> update(@PathVariable int id, @RequestBody @Valid ClientDto dto,
                                            @AuthenticationPrincipal AppUserDetails userDetails) {

        try {
            ClientDto user = clientService.update(id, dto, userDetails);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // IMPLEMENTER TEST UNICITE EMAIL !!!
        } catch (EmailAlreadyUsedException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (AccountNotOwnedException e) {
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
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
    @IsClient
    public ResponseEntity<Void> patch(@PathVariable int id, @RequestBody PasswordDto dto,
                                      @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            clientService.updatePassword(id, dto, userDetails);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadPasswordException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (AccountNotOwnedException e) {
            return new  ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
