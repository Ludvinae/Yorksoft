package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class KnowledgeController {

    protected KnowledgeDao knowledgeDao;

    @Autowired
    public KnowledgeController(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    @GetMapping("/knowledge/list")
    public List<Knowledge> getAll() {
        return knowledgeDao.findAll();
    }

    @GetMapping("/knowledge/{id}")
    public ResponseEntity<Knowledge> getById(@PathVariable int id) {

        Optional<Knowledge> knowledge = knowledgeDao.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(knowledge.get(), HttpStatus.OK);
    }

    @PostMapping("/knowledge")
    public ResponseEntity<Knowledge> create(@RequestBody @Validated(OnCreate.class) Knowledge knowledge) {
        knowledge.setIdKnowledge(null);
        knowledgeDao.save(knowledge);

        return new ResponseEntity<>(knowledge, HttpStatus.CREATED);
    }

    @DeleteMapping("/knowledge/{id}")
    public ResponseEntity<Knowledge> delete(@PathVariable int id) {
        Optional<Knowledge> knowledge = knowledgeDao.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        knowledgeDao.delete(knowledge.get());
        return new ResponseEntity<>(knowledge.get(), HttpStatus.OK);
    }

    @PutMapping("/knowledge/{id}")
    public ResponseEntity<Knowledge> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Knowledge knowledgeToUpdate) {
        Optional<Knowledge> knowledge = knowledgeDao.findById(id);

        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        knowledgeToUpdate.setIdKnowledge(knowledge.get().getIdKnowledge());
        knowledgeDao.save(knowledgeToUpdate);

        return new ResponseEntity<>(knowledge.get(), HttpStatus.OK);
    }
}
