package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iTechnicianService;
import com.mns.cda.suivimns.view.TechnicianView;
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
@RequestMapping("/technician")
public class TechnicianController {

    protected final iTechnicianService technicianservice;

    @GetMapping("/list")
    @JsonView(TechnicianView.class)
    public List<Technician> getAll() {
        return technicianservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technician> getById(@PathVariable int id) {

        Optional<Technician> technician = technicianservice.findById(id);
        if (technician.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(technician.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Technician> create(@RequestBody @Validated(OnCreate.class) Technician technician) {
        technicianservice.save(technician);

        return new ResponseEntity<>(technician, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Technician> technician = technicianservice.findById(id);
        if (technician.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        technicianservice.delete(technician.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Technician technicianToUpdate) throws iTechnicianService.TechnicianNotFoundException {
        try {
            technicianservice.update(technicianToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (iTechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
