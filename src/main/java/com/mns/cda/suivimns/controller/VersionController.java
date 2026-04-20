package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iVersionService;
import com.mns.cda.suivimns.view.SoftwareView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionController {

    protected final iVersionService iVersionService;

    @GetMapping("/list")
    @JsonView(SoftwareView.class)
    public List<Version> findAll() {
        return iVersionService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(SoftwareView.class)
    public ResponseEntity<Version> findById(@PathVariable Integer id) {

        Optional<Version> version = iVersionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(version.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Version> create(@RequestBody @Validated(OnCreate.class) Version version) {

        version.setIdVersion(null);

        iVersionService.save(version);
        return new ResponseEntity<>(version, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Version> delete(@PathVariable Integer id) {

        Optional<Version> version = iVersionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iVersionService.delete(version.get());
        return new ResponseEntity<>(version.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Version versionToModify) {
        Optional<Version> version = iVersionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionToModify.setIdVersion(id);
        iVersionService.save(versionToModify);

        if (versionToModify.getPublicationDate() == null) {
            versionToModify.setPublicationDate(version.get().getPublicationDate());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
