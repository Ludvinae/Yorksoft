package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    protected final iStatusService statusService;

    @GetMapping("/list")
    public List<Status> getAll() {
        return statusService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getById(@PathVariable int id) {

        Optional<Status> status = statusService.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Status> create(@RequestBody @Validated(OnCreate.class) Status status) {
        Status statusSaved = statusService.save(status);

        return new ResponseEntity<>(statusSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Status> status = statusService.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusService.delete(status.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Status statusToUpdate) {
        try {
            Status statusSaved = statusService.update(statusToUpdate, id);
            return new ResponseEntity<>(statusSaved, HttpStatus.OK);
        } catch (iStatusService.StatusNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
