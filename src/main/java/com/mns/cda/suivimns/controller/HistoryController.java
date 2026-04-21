package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.History;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iHistoryService;
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
@RequestMapping("/history")
public class HistoryController {

    protected final iHistoryService iHistoryService;

    @GetMapping("/list")
    public List<History> getAll() {
        return iHistoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable int id) {

        Optional<History> history = iHistoryService.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<History> create(@RequestBody @Validated(OnCreate.class) History history) {
        history.setIdHistory(null);
        iHistoryService.save(history);

        return new ResponseEntity<>(history, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<History> history = iHistoryService.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iHistoryService.delete(history.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) History historyToUpdate) {
        Optional<History> history = iHistoryService.findById(id);

        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        historyToUpdate.setIdHistory(history.get().getIdHistory());
        iHistoryService.save(historyToUpdate);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
