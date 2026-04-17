package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iClassificationService;
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
@RequestMapping("/classification")
public class ClassificationController {

    protected final iClassificationService classificationservice;

    @GetMapping("/list")
    public List<Classification> getAll() {
        return classificationservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classification> getById(@PathVariable int id) {

        Optional<Classification> classification = classificationservice.findById(id);
        if (classification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(classification.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Classification> create(@RequestBody @Validated(OnCreate.class) Classification classification) {
        classificationservice.save(classification);

        return new ResponseEntity<>(classification, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Classification> classification = classificationservice.findById(id);
        if (classification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        classificationservice.delete(classification.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Classification classificationToUpdate) throws iClassificationService.ClassificationNotFoundException {
        try {
            classificationservice.update(classificationToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (iClassificationService.ClassificationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
