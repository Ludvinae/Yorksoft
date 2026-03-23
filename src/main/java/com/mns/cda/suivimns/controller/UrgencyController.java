package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.UrgencyDao;
import com.mns.cda.suivimns.model.Urgency;
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
public class UrgencyController {

    protected UrgencyDao urgencyDao;

    @Autowired
    public UrgencyController(UrgencyDao urgencyDao) {
        this.urgencyDao = urgencyDao;
    }

    @GetMapping("/urgency/list")
    public List<Urgency> getAll() {
        return urgencyDao.findAll();
    }

    @GetMapping("/urgency/{id}")
    public ResponseEntity<Urgency> getById(@PathVariable int id) {

        Optional<Urgency> urgency = urgencyDao.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(urgency.get(), HttpStatus.OK);
    }

    @PostMapping("/urgency")
    public ResponseEntity<Urgency> create(@RequestBody @Validated(OnCreate.class) Urgency urgency) {
        urgency.setIdUrgency(null);
        urgencyDao.save(urgency);

        return new ResponseEntity<>(urgency, HttpStatus.CREATED);
    }

    @DeleteMapping("/urgency/{id}")
    public ResponseEntity<Urgency> delete(@PathVariable int id) {
        Optional<Urgency> urgency = urgencyDao.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        urgencyDao.delete(urgency.get());
        return new ResponseEntity<>(urgency.get(), HttpStatus.OK);
    }

    @PutMapping("/urgency/{id}")
    public ResponseEntity<Urgency> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Urgency urgencyToUpdate) {
        Optional<Urgency> urgency = urgencyDao.findById(id);

        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        urgencyToUpdate.setIdUrgency(urgency.get().getIdUrgency());
        urgencyDao.save(urgencyToUpdate);

        return new ResponseEntity<>(urgency.get(), HttpStatus.OK);
    }
}
