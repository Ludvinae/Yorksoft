package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.AppUserService;
import com.mns.cda.suivimns.service.ClientService;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import com.mns.cda.suivimns.view.ClientView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClientController {

    protected final ClientService clientService;

    @GetMapping("/list")
    public List<ClientDto> getAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable int id) {

        ClientDto client = clientService.findDtoById(id);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    /*
    @GetMapping("/{id}/software/list")
    @JsonView(ClientSoftwareListView.class)
    public ResponseEntity<Client> getClientSofwareList(@PathVariable int id) {

        Optional<Client> client = clientService.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

     */


    @PostMapping("/")
    public ResponseEntity<Client> create(@RequestBody @Validated(OnCreate.class) Client client) {
        clientService.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable int id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientService.delete(client.get());
        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Client clientToUpdate) throws ClientService.ClientNotFoundException {
        try {
            clientService.update(clientToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
