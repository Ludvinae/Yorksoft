package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.OrganisationDao;
import com.mns.cda.suivimns.model.Organisation;
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
public class OrganisationController {

    protected OrganisationDao organisationDao;

    @Autowired
    public OrganisationController(OrganisationDao organisationDao) {
        this.organisationDao = organisationDao;
    }

    @GetMapping("/organisation/list")
    public List<Organisation> getAll() {
        return organisationDao.findAll();
    }

    @GetMapping("/organisation/{id}")
    public ResponseEntity<Organisation> getById(@PathVariable int id) {

        Optional<Organisation> organisation = organisationDao.findById(id);
        if (organisation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(organisation.get(), HttpStatus.OK);
    }

    @PostMapping("/organisation")
    public ResponseEntity<Organisation> create(@RequestBody @Validated(OnCreate.class) Organisation organisation) {
        organisation.setIdOrganisation(null);
        organisationDao.save(organisation);

        return new ResponseEntity<>(organisation, HttpStatus.CREATED);
    }

    @DeleteMapping("/organisation/{id}")
    public ResponseEntity<Organisation> delete(@PathVariable int id) {
        Optional<Organisation> organisation = organisationDao.findById(id);
        if (organisation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organisationDao.delete(organisation.get());
        return new ResponseEntity<>(organisation.get(), HttpStatus.OK);
    }

    @PutMapping("/organisation/{id}")
    public ResponseEntity<Organisation> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Organisation organisationToUpdate) {
        Optional<Organisation> organisation = organisationDao.findById(id);

        if (organisation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organisationToUpdate.setIdOrganisation(organisation.get().getIdOrganisation());
        organisationDao.save(organisationToUpdate);

        return new ResponseEntity<>(organisation.get(), HttpStatus.OK);
    }
}
