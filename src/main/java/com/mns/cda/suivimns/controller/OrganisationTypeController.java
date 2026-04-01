package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.OrganisationTypeDao;
import com.mns.cda.suivimns.model.OrganisationType;
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
public class OrganisationTypeController {

    protected OrganisationTypeDao organisationTypeDao;

    @Autowired
    public OrganisationTypeController(OrganisationTypeDao organisationTypeDao) {
        this.organisationTypeDao = organisationTypeDao;
    }

    @GetMapping("/organisationType/list")
    public List<OrganisationType> getAll() {
        return organisationTypeDao.findAll();
    }

    @GetMapping("/organisationType/{id}")
    public ResponseEntity<OrganisationType> getById(@PathVariable int id) {

        Optional<OrganisationType> organisationType = organisationTypeDao.findById(id);
        if (organisationType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(organisationType.get(), HttpStatus.OK);
    }

    @PostMapping("/organisationType")
    public ResponseEntity<OrganisationType> create(@RequestBody @Validated(OnCreate.class) OrganisationType organisationType) {
        organisationType.setIdOrganisationType(null);
        organisationTypeDao.save(organisationType);

        return new ResponseEntity<>(organisationType, HttpStatus.CREATED);
    }

    @DeleteMapping("/organisationType/{id}")
    public ResponseEntity<OrganisationType> delete(@PathVariable int id) {
        Optional<OrganisationType> organisationType = organisationTypeDao.findById(id);
        if (organisationType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organisationTypeDao.delete(organisationType.get());
        return new ResponseEntity<>(organisationType.get(), HttpStatus.OK);
    }

    @PutMapping("/organisationType/{id}")
    public ResponseEntity<OrganisationType> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) OrganisationType organisationTypeToUpdate) {
        Optional<OrganisationType> organisationType = organisationTypeDao.findById(id);

        if (organisationType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organisationTypeToUpdate.setIdOrganisationType(organisationType.get().getIdOrganisationType());
        organisationTypeDao.save(organisationTypeToUpdate);

        return new ResponseEntity<>(organisationType.get(), HttpStatus.OK);
    }
}
