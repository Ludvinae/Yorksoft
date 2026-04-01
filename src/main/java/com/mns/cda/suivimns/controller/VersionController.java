package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.VersionDao;
import com.mns.cda.suivimns.model.Version;
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
public class VersionController {

    protected VersionDao versionDao;

    @Autowired
    public VersionController(VersionDao versionDao) {
        this.versionDao = versionDao;
    }

    @GetMapping("/version/list")
    public List<Version> findAll() {
        return versionDao.findAll();
    }

    @GetMapping("/version/{id}")
    public ResponseEntity<Version> findById(@PathVariable Integer id) {

        Optional<Version> version = versionDao.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(version.get(), HttpStatus.OK);
    }

    @PostMapping("/version")
    public ResponseEntity<Version> create(@RequestBody @Validated(OnCreate.class) Version version) {

        version.setIdVersion(null);

        versionDao.save(version);
        return new ResponseEntity<>(version, HttpStatus.CREATED);
    }

    @DeleteMapping("/version/{id}")
    public ResponseEntity<Version> delete(@PathVariable Integer id) {

        Optional<Version> version = versionDao.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionDao.deleteById(id);
        return new ResponseEntity<>(version.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/version/{id}")
    public ResponseEntity<Version> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Version versionToModify) {
        Optional<Version> version = versionDao.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionToModify.setIdVersion(id);
        versionDao.save(versionToModify);

        if (versionToModify.getPublicationDate() == null) {
            versionToModify.setPublicationDate(version.get().getPublicationDate());
        }

        return new ResponseEntity<>(versionToModify, HttpStatus.OK);
    }
}
