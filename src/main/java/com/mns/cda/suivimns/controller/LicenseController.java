package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/license")
public class LicenseController {

    protected final LicenseService licenseservice;

    @GetMapping("/list")
    public List<License> getAll() {
        return licenseservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<License> getById(@PathVariable int id) {

        Optional<License> license = licenseservice.findById(id);
        if (license.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(license.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<License> create(@RequestBody @Validated(OnCreate.class) License license) {
        licenseservice.save(license);

        return new ResponseEntity<>(license, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<License> license = licenseservice.findById(id);
        if (license.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        licenseservice.delete(license.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) License licenseToUpdate) throws LicenseService.LicenseNotFoundException {
        try {
            licenseservice.update(licenseToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LicenseService.LicenseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
