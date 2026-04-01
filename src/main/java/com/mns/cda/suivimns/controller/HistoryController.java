package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.HistoryDao;
import com.mns.cda.suivimns.model.History;
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
@CrossOrigin
public class HistoryController {

    protected HistoryDao historyDao;

    @Autowired
    public HistoryController(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }

    @GetMapping("/history/list")
    public List<History> getAll() {
        return historyDao.findAll();
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<History> getById(@PathVariable int id) {

        Optional<History> history = historyDao.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }

    @PostMapping("/history")
    public ResponseEntity<History> create(@RequestBody @Validated(OnCreate.class) History history) {
        history.setIdHistory(null);
        historyDao.save(history);

        return new ResponseEntity<>(history, HttpStatus.CREATED);
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<History> delete(@PathVariable int id) {
        Optional<History> history = historyDao.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        historyDao.delete(history.get());
        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }

    @PutMapping("/history/{id}")
    public ResponseEntity<History> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) History historyToUpdate) {
        Optional<History> history = historyDao.findById(id);

        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        historyToUpdate.setIdHistory(history.get().getIdHistory());
        historyDao.save(historyToUpdate);
        
        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }
}
