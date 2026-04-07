package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.AppUserService;
import com.mns.cda.suivimns.service.ClassificationService;
import com.mns.cda.suivimns.service.TicketService;
import com.mns.cda.suivimns.view.TicketView;
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

    protected final ClassificationService classificationservice;

    @GetMapping("/list")
    @JsonView(TicketView.class)
    public List<Ticket> getAll() {
        return classificationservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id) {

        Optional<Ticket> ticket = classificationservice.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> create(@RequestBody @Validated(OnCreate.class) Ticket ticket) {
        classificationservice.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Ticket> ticket = classificationservice.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        classificationservice.delete(ticket.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Ticket ticketToUpdate) throws TicketService.TicketNotFoundException {
        try {
            classificationservice.update(ticketToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TicketService.TicketNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
