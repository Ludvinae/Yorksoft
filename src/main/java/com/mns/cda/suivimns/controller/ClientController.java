package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.ClientSoftwareListView;
import com.mns.cda.suivimns.view.ClientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ClientController {

    protected ClientDao clientDao;

    @Autowired
    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping("/client/list")
    @JsonView(ClientView.class)
    public List<Client> getAll() {
        return clientDao.findAll();
    }

    @GetMapping("/client/{id}")
    @JsonView(ClientView.class)
    public ResponseEntity<Client> getById(@PathVariable int id) {

        Optional<Client> client = clientDao.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    @GetMapping("/client/{id}/software/list")
    @JsonView(ClientSoftwareListView.class)
    public ResponseEntity<Client> getClientSofwareList(@PathVariable int id) {

        Optional<Client> client = clientDao.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    /*
    @PostMapping("/client")
    public ResponseEntity<Client> create(@RequestBody @Validated(OnCreate.class) Client client) {
        client.setIdClient(null);
        clientDao.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }



    @DeleteMapping("/client/{id}")
    public ResponseEntity<Client> delete(@PathVariable int id) {
        Optional<Client> client = clientDao.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientDao.delete(client.get());
        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Client clientToUpdate) {
        Optional<Client> client = clientDao.findById(id);

        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientToUpdate.setIdClient(client.get().getIdClient());
        clientToUpdate.setPassword(client.get().getPassword());
        clientDao.save(clientToUpdate);
        return new ResponseEntity<>(client.get(), HttpStatus.OK);
    }
    */

}
