package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dto.TicketCreation;
import com.mns.cda.suivimns.dto.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.TicketResponse;
import com.mns.cda.suivimns.model.*;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iTicketService;
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

    protected final iTicketService ticketService;


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
    public ResponseEntity<TicketResponse> getById(@PathVariable int id) {

        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticketService.responseToDto(ticket.get()), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody @Validated(OnCreate.class) TicketCreation ticketCreated) {

        Ticket ticket = ticketService.createTicket(ticketCreated);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketService.responseToDto(ticket));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ticketService.delete(ticket.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Ticket ticketToUpdate) {
       try {
           ticketService.update(ticketToUpdate, id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (iTicketService.TicketNotFoundException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
