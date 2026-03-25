package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.model.VersionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VersionTypeController {

    private final VersionTypeDao versionTypeDao;

    @Autowired
    public VersionTypeController(VersionTypeDao versionTypeDao) {
        this.versionTypeDao = versionTypeDao;
    }

    @GetMapping("/version-type/list")
    public List<VersionType> getAll() {
        return versionTypeDao.findAll();
    }

    @GetMapping("/version-type/{id}")
    public ResponseEntity<VersionType> getById(@PathVariable Integer id) {
        Optional<VersionType> versionType = versionTypeDao.findById(id);

        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(versionType.get() , HttpStatus.OK);
    }

    @PostMapping("/version-type")
    public ResponseEntity<VersionType> create(@RequestBody @Validated() VersionType versionType) {
        versionType.setIdVersionType(null);

        versionTypeDao.save(versionType);
        return new ResponseEntity<>(versionType , HttpStatus.CREATED);
    }

    @DeleteMapping("/version-type/{id}")
    public ResponseEntity<VersionType> delete(@PathVariable Integer id) {
        Optional<VersionType> versionType = versionTypeDao.findById(id);
        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionTypeDao.delete(versionType.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/version-type/{id}")
    public ResponseEntity<VersionType> update(@PathVariable Integer id, @RequestBody @Validated() VersionType typeToUpdate) {
        Optional<VersionType> versionType = versionTypeDao.findById(id);

        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeToUpdate.setIdVersionType(id);
        versionTypeDao.save(typeToUpdate);

        return new ResponseEntity<>(typeToUpdate, HttpStatus.OK);
    }
}
