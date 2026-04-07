package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.StatusService;
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
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusController {

    protected final StatusService statusService;

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

    @PostMapping("/")
    public ResponseEntity<Status> create(@RequestBody @Validated(OnCreate.class) Status status) {
        status.setIdStatus(null);
        statusService.save(status);

        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Status> delete(@PathVariable int id) {
        Optional<Status> status = statusService.findById(id);
        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusService.delete(status.get());
        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Status statusToUpdate) {
        Optional<Status> status = statusService.findById(id);

        if (status.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        statusToUpdate.setIdStatus(status.get().getIdStatus());
        statusService.save(statusToUpdate);

        return new ResponseEntity<>(status.get(), HttpStatus.OK);
    }
}
