package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iClientService;
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

@RestController
@CrossOrigin
@RequestMapping("/client")
@RequiredArgsConstructor
@Tag(name = "Client", description = "Gestion des clients")
public class ClientController {

    protected final iClientService clientService;

    @Operation(summary = "Récupérer tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    public List<ClientDto> getAll() {
        return clientService.findAll();
    }

    @Operation(summary = "Récupérer un client par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client trouvé"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable int id) {

        Optional<ClientDto> client = clientService.findDtoById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Validated(OnCreate.class) Client client) {
        Client clientSaved = clientService.save(client);

        ClientDto dto = clientService.toDto(clientSaved);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Operation(summary = "Supprimer un client")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Client supprimé"),
            @ApiResponse(responseCode = "404", description = "Client non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientService.delete(client.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Mettre à jour un client")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Client mis à jour"),
                 @ApiResponse(responseCode = "404", description = "Client non trouvé"),
                 @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Client clientToUpdate){
        try {
            clientService.update(clientToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
