package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.TicketDao;
import com.mns.cda.suivimns.model.Ticket;
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
public class TicketController {

    protected TicketDao ticketDao;

    @Autowired
    public TicketController(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GetMapping("/ticket/list")
    public List<Ticket> getAll() {
        return ticketDao.findAll();
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id) {

        Optional<Ticket> ticket = ticketDao.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

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
