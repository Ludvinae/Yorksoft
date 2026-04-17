package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iImpactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/impact")
public class ImpactController {

    protected final iImpactService iImpactService;

    @GetMapping("/list")
    public List<Impact> getAll() {
        return iImpactService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Impact> getById(@PathVariable int id) {

        Optional<Impact> impact = iImpactService.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Impact> create(@RequestBody @Validated(OnCreate.class) Impact impact) {
        impact.setIdImpact(null);
        iImpactService.save(impact);

        return new ResponseEntity<>(impact, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Impact> delete(@PathVariable int id) {
        Optional<Impact> impact = iImpactService.findById(id);
        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iImpactService.delete(impact.get());
        return new ResponseEntity<>(impact.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Impact> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Impact impactToUpdate) {
        Optional<Impact> impact = iImpactService.findById(id);

        if (impact.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        impactToUpdate.setIdImpact(impact.get().getIdImpact());
        iImpactService.save(impactToUpdate);

        return new ResponseEntity<>(impact.get(), HttpStatus.OK);
    }
}
