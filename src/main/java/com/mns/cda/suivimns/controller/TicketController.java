package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
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
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    protected final TicketService ticketService;


    @GetMapping("/list")
    @JsonView(TicketView.class)
    public List<Ticket> getAll() {
        return ticketService.findAll();
    }

    @GetMapping("/list-full-latest")
    public List<TicketFullWithLatest> getTicketFullLatest() {
        return ticketService.getAllTicketFullWithLatest();
    }

    @GetMapping("/list-full-latest/{id}")
    public List<TicketFullWithLatest> getTicketFullWithLatestByTechnician(@PathVariable Integer id) {
        return ticketService.getTicketFullWithLatestByTechnician(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id) {

        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

    /*
    @GetMapping("/ticket/{id}/status/list")
    @JsonView(TicketStatusListView.class)
    public ResponseEntity<Ticket> getStatusList(@PathVariable int id) {

        Optional<Ticket> ticket = ticketDao.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

    @GetMapping("/ticket/{id}/status/latest")
    @JsonView(TicketStatusListView.class)
    public ResponseEntity<Status> getLatestStatus(@PathVariable int id) {

        Optional<Ticket> ticket = ticketDao.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        History history = ticket.get().getHistoryList().get(0);
        Status status = history.getStatus();

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/ticket/{id}/theme/latest")
    @JsonView(TicketStatusListView.class)
    public ResponseEntity<Theme> getLatestTheme(@PathVariable int id) {

        Optional<Ticket> ticket = ticketDao.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Classification classification = ticket.get().getClassificationList().getLast();
        Theme theme = classification.getTheme();

        return new ResponseEntity<>(theme, HttpStatus.OK);
    }

     */



    @PostMapping("/")
    public ResponseEntity<Ticket> create(@RequestBody @Validated(OnCreate.class) Ticket ticket) {
        ticketService.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Ticket> createWithStatusUpdate(@RequestBody @Validated(OnCreate.class) Ticket ticket,
                                                         @PathVariable Integer id) {
        ticketService.saveWithStatusUpdate(ticket, id);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ticketService.delete(ticket.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Ticket ticketToUpdate) throws TicketService.TicketNotFoundException {
       try {
           ticketService.update(ticketToUpdate, id);
           return new ResponseEntity<>(HttpStatus.OK);
       } catch (TicketService.TicketNotFoundException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
