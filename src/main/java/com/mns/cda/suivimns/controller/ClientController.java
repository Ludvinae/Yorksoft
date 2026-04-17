package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iClientService;
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
public class ClientController {

    protected final iClientService iClientService;

    @GetMapping("/list")
    public List<ClientDto> getAll() {
        return iClientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable int id) {

        ClientDto client = iClientService.findDtoById(id);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Validated(OnCreate.class) Client client) {
        iClientService.save(client);

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable int id) {
        Optional<Client> client = iClientService.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iClientService.delete(client.get());
        return new ResponseEntity<>(client.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Client clientToUpdate) throws iClientService.ClientNotFoundException {
        try {
            iClientService.update(clientToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iClientService.ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
