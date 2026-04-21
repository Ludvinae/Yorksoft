package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iDirectorService;
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
@RequestMapping("/director")
public class DirectorController {

    protected final iDirectorService directorService;

    @GetMapping("/list")
    public List<Director> getAll() {
        return directorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getById(@PathVariable int id) {

        Optional<Director> director = directorService.findById(id);
        if (director.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(director.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Director> create(@RequestBody @Validated(OnCreate.class) Director director) {
        Director directorSaved = directorService.save(director);

        return new ResponseEntity<>(directorSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Director> director = directorService.findById(id);
        if (director.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        directorService.delete(director.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Director directorToUpdate) {
        try {
            directorService.update(directorToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iDirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
