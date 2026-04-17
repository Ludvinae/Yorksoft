package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iManagerService;
import com.mns.cda.suivimns.view.ManagerView;
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
@RequestMapping("/manager")
public class ManagerController {

    protected final iManagerService managerservice;

    @GetMapping("/list")
    @JsonView(ManagerView.class)
    public List<Manager> getAll() {
        return managerservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manager> getById(@PathVariable int id) {

        Optional<Manager> manager = managerservice.findById(id);
        if (manager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(manager.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Manager> create(@RequestBody @Validated(OnCreate.class) Manager manager) {
        managerservice.save(manager);

        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Manager> manager = managerservice.findById(id);
        if (manager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        managerservice.delete(manager.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Manager managerToUpdate) throws iManagerService.ManagerNotFoundException {
        try {
            managerservice.update(managerToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (iManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
