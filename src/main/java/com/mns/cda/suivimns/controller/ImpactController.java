package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.model.Impact;
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
public class ImpactController {

    protected ImpactDao impactDao;

    @Autowired
    public ImpactController(ImpactDao impactDao) {
        this.impactDao = impactDao;
    }

    @GetMapping("/impact/list")
    public List<Impact> getAll() {
        return impactDao.findAll();
    }

    @GetMapping("/impact/{id}")
    public ResponseEntity<Impact> getById(@PathVariable int id) {

        Optional<Impact> impact = impactDao.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }

    @PostMapping("/impact")
    public ResponseEntity<Impact> create(@RequestBody @Validated(OnCreate.class) Impact impact) {
        impact.setIdImpact(null);
        impactDao.save(impact);

        return new ResponseEntity<>(impact, HttpStatus.CREATED);
    }

    @DeleteMapping("/impact/{id}")
    public ResponseEntity<Impact> delete(@PathVariable int id) {
        Optional<Impact> impact = impactDao.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        impactDao.delete(impact.get());
        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }

    @PutMapping("/impact/{id}")
    public ResponseEntity<Impact> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Impact impactToUpdate) {
        Optional<Impact> impact = impactDao.findById(id);

        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        impactToUpdate.setIdImpact(impact.get().getIdImpact());
        impactDao.save(impactToUpdate);

        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }
}
