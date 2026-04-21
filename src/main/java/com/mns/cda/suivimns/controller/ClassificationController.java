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

    protected final iClassificationService classificationService;

    @GetMapping("/list")
    public List<Classification> getAll() {
        return classificationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classification> getById(@PathVariable int id) {

        Optional<Classification> classification = classificationService.findById(id);
        if (classification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(classification.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Classification> create(@RequestBody @Validated(OnCreate.class) Classification classification) {
        classificationService.save(classification);

        return new ResponseEntity<>(classification, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Classification> classification = classificationService.findById(id);
        if (classification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        classificationService.delete(classification.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Classification classificationToUpdate) {
        try {
            classificationService.update(classificationToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iClassificationService.ClassificationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
