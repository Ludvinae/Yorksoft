package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.CommunicationCanalDao;
import com.mns.cda.suivimns.model.CommunicationCanal;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CommunicationCanalController {

    protected CommunicationCanalDao communicationCanalDao;

    @Autowired
    public CommunicationCanalController(CommunicationCanalDao communicationCanalDao) {
        this.communicationCanalDao = communicationCanalDao;
    }

    @GetMapping("/communicationCanal/list")
    public List<CommunicationCanal> getAll() {
        return communicationCanalDao.findAll();
    }

    @GetMapping("/communicationCanal/{id}")
    public ResponseEntity<CommunicationCanal> getById(@PathVariable int id) {

        Optional<CommunicationCanal> communicationCanal = communicationCanalDao.findById(id);
        if (communicationCanal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(communicationCanal.get(), HttpStatus.OK);
    }

    @PostMapping("/communicationCanal")
    public ResponseEntity<CommunicationCanal> create(@RequestBody @Validated(OnCreate.class) CommunicationCanal communicationCanal) {
        communicationCanal.setIdCanal(null);
        communicationCanalDao.save(communicationCanal);

        return new ResponseEntity<>(communicationCanal, HttpStatus.CREATED);
    }

    @DeleteMapping("/communicationCanal/{id}")
    public ResponseEntity<CommunicationCanal> delete(@PathVariable int id) {
        Optional<CommunicationCanal> communicationCanal = communicationCanalDao.findById(id);
        if (communicationCanal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        communicationCanalDao.delete(communicationCanal.get());
        return new ResponseEntity<>(communicationCanal.get(), HttpStatus.OK);
    }

    @PutMapping("/communicationCanal/{id}")
    public ResponseEntity<CommunicationCanal> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) CommunicationCanal communicationCanalToUpdate) {
        Optional<CommunicationCanal> communicationCanal = communicationCanalDao.findById(id);

        if (communicationCanal.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        communicationCanalToUpdate.setIdCanal(communicationCanal.get().getIdCanal());
        communicationCanalDao.save(communicationCanalToUpdate);
        return new ResponseEntity<>(communicationCanal.get(), HttpStatus.OK);
    }
}
