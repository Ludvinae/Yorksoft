package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
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

    protected final iVersionService versionService;

    @GetMapping("/list")
    @JsonView(SoftwareView.class)
    public List<Version> findAll() {
        return versionService.findAll();
    }

    @GetMapping("/{id}")
    @JsonView(SoftwareView.class)
    public ResponseEntity<Version> findById(@PathVariable Integer id) {

        Optional<Version> version = versionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(version.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Version> create(@RequestBody @Validated(OnCreate.class) Version version) {
        Version versionSaved = versionService.save(version);

        return new ResponseEntity<>(versionSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Optional<Version> version = versionService.findById(id);
        if (version.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        versionService.delete(version.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Champs modifiables :
     * versionNumber
     * publicationDate
     * versionType
     */
    @PutMapping("/{id}")
    public ResponseEntity<Version> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) Version versionToUpdate) {
        try {
            Version versionSaved = versionService.update(versionToUpdate, id);
            return new ResponseEntity<>(versionSaved, HttpStatus.OK);
        } catch (iVersionService.VersionNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
