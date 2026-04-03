package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.TicketService;
import com.mns.cda.suivimns.view.TicketStatusListView;
import com.mns.cda.suivimns.view.TicketView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TicketController {

    protected final TicketDao ticketDao;

    protected final TicketService ticketService;


    @GetMapping("/ticket/list")
    @JsonView(TicketView.class)
    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }

    @GetMapping("/ticket/list-full-latest")
    public List<TicketFullWithLatest> getTicketFullLatest() {
        return ticketService.getTicketFullWithLatest();
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id) {

        Optional<Ticket> ticket = ticketDao.findById(id);
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



    @PostMapping("/ticket")
    public ResponseEntity<Ticket> create(@RequestBody @Validated(OnCreate.class) Ticket ticket) {
        ticket.setIdTicket(null);
        ticketDao.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable int id) {
        Optional<Ticket> ticket = ticketDao.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ticketDao.delete(ticket.get());
        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<Ticket> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Ticket ticketToUpdate) {
        Optional<Ticket> ticket = ticketDao.findById(id);

        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ticketToUpdate.setIdTicket(ticket.get().getIdTicket());
        ticketDao.save(ticketToUpdate);

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }
}
