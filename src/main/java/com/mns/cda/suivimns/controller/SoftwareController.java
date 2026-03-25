package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.view.SoftwareVersionListView;
import com.mns.cda.suivimns.view.SoftwareView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SoftwareController {


    protected SoftwareDao softwareDao;

    @Autowired
    public SoftwareController(SoftwareDao softwareDao) {
        this.softwareDao = softwareDao;
    }

    @GetMapping("/software/list")
    @JsonView(SoftwareView.class)
    public List<Software> getAll() {
        return softwareDao.findAll();
    }

    @GetMapping("/software/{id}")
    @JsonView(SoftwareView.class)
    public ResponseEntity<Software> getById(@PathVariable Integer id) {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @GetMapping("/software/{id}/version/list")
    @JsonView(SoftwareVersionListView.class)
    public ResponseEntity<Software> getSoftwareVersionById(@PathVariable Integer id) {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @PostMapping("/software")
    public ResponseEntity<Software> create(@RequestBody @Validated(OnCreate.class) Software software) {
        software.setIdSoftware(null);
        softwareDao.save(software);

        return new ResponseEntity<>(software, HttpStatus.CREATED);
    }

    @DeleteMapping("/software/{id}")
    public ResponseEntity<Software> delete(@PathVariable Integer id) {
        Optional<Software> software = softwareDao.findById(id);
        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        softwareDao.delete(software.get());
        return new ResponseEntity<>(software.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/software/{id}")
    public ResponseEntity<Software> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Software softwareData) {
        Optional<Software> software = softwareDao.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (softwareData.getName() == null) {
            softwareData.setName(software.get().getName());
        }

        if (softwareData.getDescription() == null) {
            softwareData.setDescription(software.get().getDescription());
        }

        if (softwareData.getType() == null) {
            softwareData.setType(software.get().getType());
        }

        softwareData.setIdSoftware(id);
        softwareDao.save(softwareData);
        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }
}
