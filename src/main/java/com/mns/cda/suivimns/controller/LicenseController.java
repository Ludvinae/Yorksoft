package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.AppUserService;
import com.mns.cda.suivimns.service.LicenseService;
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
@RequestMapping("/license")
public class LicenseController {

    protected final LicenseService licenseservice;

    @GetMapping("/list")
    @JsonView(TicketView.class)
    public List<Ticket> getAll() {
        return licenseservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable int id) {

        Optional<Ticket> ticket = licenseservice.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticket.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> create(@RequestBody @Validated(OnCreate.class) Ticket ticket) {
        licenseservice.save(ticket);

        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Ticket> ticket = licenseservice.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        licenseservice.delete(ticket.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Ticket ticketToUpdate) throws TicketService.TicketNotFoundException {
        try {
            licenseservice.update(ticketToUpdate, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TicketService.TicketNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
