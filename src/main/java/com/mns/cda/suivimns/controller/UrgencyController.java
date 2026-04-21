package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.Urgency;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iUrgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/urgency")
@RequiredArgsConstructor
public class UrgencyController {

    protected final iUrgencyService urgencyService;

    @GetMapping("/list")
    public List<Urgency> getAll() {
        return urgencyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Urgency> getById(@PathVariable int id) {

        Optional<Urgency> urgency = urgencyService.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(urgency.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Urgency> create(@RequestBody @Validated(OnCreate.class) Urgency urgency) {
        Urgency urgencySaved = urgencyService.save(urgency);

        return new ResponseEntity<>(urgencySaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Urgency> urgency = urgencyService.findById(id);
        if (urgency.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        urgencyService.delete(urgency.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Urgency> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Urgency urgencyToUpdate) {
        try {
            Urgency urgencySaved = urgencyService.update(urgencyToUpdate, id);
            return new ResponseEntity<>(urgencySaved, HttpStatus.OK);
        } catch (iUrgencyService.UrgencyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
