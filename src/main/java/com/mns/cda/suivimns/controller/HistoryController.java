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

    protected final iHistoryService historyService;

    @GetMapping("/list")
    public List<History> getAll() {
        return historyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getById(@PathVariable int id) {

        Optional<History> history = historyService.findById(id);
        if (history.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(history.get(), HttpStatus.OK);
    }

    // Pas de route CREATE, ce sont les actions des utilisateurs qui provoquent un changement de statut
    // et donc créent une nouvelle entrée dans la table History
    /*
    @PostMapping
    public ResponseEntity<History> create(@RequestBody @Validated(OnCreate.class) History history) {
        History historySaved = historyService.save(history);

        return new ResponseEntity<>(historySaved, HttpStatus.CREATED);
    }

     */

    // Pas de route DELETE et PUT, on ne modifie pas l'historique passé
}
