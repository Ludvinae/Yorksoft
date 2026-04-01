package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
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
public class StatusController {

    protected StatusDao statusDao;

    @Autowired
    public StatusController(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @GetMapping("/status/list")
    public List<Status> getAll() {
        return statusDao.findAll();
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Status> getById(@PathVariable int id) {

        Optional<Status> status = statusDao.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<Status> create(@RequestBody @Validated(OnCreate.class) Status status) {
        status.setIdStatus(null);
        statusDao.save(status);

        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/status/{id}")
    public ResponseEntity<Status> delete(@PathVariable int id) {
        Optional<Status> status = statusDao.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusDao.delete(status.get());
        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Status> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Status statusToUpdate) {
        Optional<Status> status = statusDao.findById(id);

        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusToUpdate.setIdStatus(status.get().getIdStatus());
        statusDao.save(statusToUpdate);

        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }
}
