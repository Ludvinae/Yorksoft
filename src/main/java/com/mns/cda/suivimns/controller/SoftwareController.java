package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.SoftwareService;
import com.mns.cda.suivimns.view.SoftwareVersionListView;
import com.mns.cda.suivimns.view.SoftwareView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/software")
@RequiredArgsConstructor
public class SoftwareController {


    protected SoftwareService softwareService;

    @Autowired
    public SoftwareController(SoftwareDao softwareDao) {
        this.softwareService = softwareDao;
    }

    @GetMapping("/list")
    @JsonView(SoftwareView.class)
    public List<Software> getAll() {
        return softwareService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(SoftwareView.class)
    public ResponseEntity<Software> getById(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}/version/list")
    @JsonView(SoftwareVersionListView.class)
    public ResponseEntity<Software> getSoftwareVersionById(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);

        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Software> create(@RequestBody @Validated(OnCreate.class) Software software) {
        software.setIdSoftware(null);
        softwareService.save(software);

        return new ResponseEntity<>(software, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Software> delete(@PathVariable Integer id) {
        Optional<Software> software = softwareService.findById(id);
        if (software.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        softwareService.delete(software.get());
        return new ResponseEntity<>(software.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Software> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Software softwareData) {
        Optional<Software> software = softwareService.findById(id);

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
        softwareService.save(softwareData);
        return new ResponseEntity<>(software.get(), HttpStatus.OK);
    }
}
