package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iKnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    protected final iKnowledgeService knowledgeService;

    @GetMapping("/list")
    public List<Knowledge> getAll() {
        return knowledgeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Knowledge> getById(@PathVariable int id) {

        Optional<Knowledge> knowledge = knowledgeService.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(knowledge.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Knowledge> create(@RequestBody @Validated(OnCreate.class) Knowledge knowledge) {
        Knowledge knowledgeSaved = knowledgeService.save(knowledge);

        return new ResponseEntity<>(knowledgeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Knowledge> knowledge = knowledgeService.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        knowledgeService.delete(knowledge.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Knowledge knowledgeToUpdate) {
        Optional<Knowledge> knowledge = knowledgeService.findById(id);

        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        knowledgeToUpdate.setIdKnowledge(knowledge.get().getIdKnowledge());
        knowledgeService.save(knowledgeToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
