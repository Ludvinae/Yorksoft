package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.dto.flat.TicketUpdatedDto;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ticket", description = "Gestion des tickets de support (création, suivi, affectation, résolution)")
public class TicketController {

    protected final TicketService ticketService;


    @Operation(
            summary = "Lister tous les tickets",
            description = "Retourne la liste des tickets avec leurs informations principales")
    @ApiResponse(responseCode = "200", description = "Liste des tickets récupérée")
    @GetMapping("/list")
    public List<TicketResponse> getAll() {
        return ticketService.findAllDto();
    }


    @Operation(
            summary = "Lister les tickets avec détails complets",
            description = "Retourne les tickets avec leur historique récent, statut et affectation actuelle")
    @ApiResponse(responseCode = "200", description = "Liste détaillée récupérée")
    @GetMapping("/list/full")
    public List<TicketFullWithLatest> getTicketFullLatest() {
        return ticketService.getAllTicketFullWithLatest();
    }


    @Operation(
            summary = "Lister les tickets d’un technicien",
            description = "Retourne les tickets assignés à un technicien avec leur dernier état")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tickets récupérés"),
            @ApiResponse(responseCode = "404", description = "Technicien non trouvé")})
    @GetMapping("/list/technician/{id}")
    public ResponseEntity<List<TicketFullWithLatest>> getTicketFullWithLatestByTechnician(@PathVariable Integer id) {
        List<TicketFullWithLatest> tickets = ticketService.getTicketFullWithLatestByTechnician(id);

        if (tickets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @Operation(
            summary = "Récupérer un ticket par ID",
            description = "Retourne les informations principales d’un ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket trouvé"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getById(@PathVariable int id) {

        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticketService.responseToDto(ticket.get()), HttpStatus.OK);
    }


    @Operation(
            summary = "Créer un ticket",
            description = "Crée un nouveau ticket à partir d’une demande client (description, urgence, impact, etc.)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody @Validated(OnCreate.class) TicketCreation ticketCreated) {

        Ticket ticket = ticketService.createTicket(ticketCreated);

        return new ResponseEntity<>(ticketService.responseToDto(ticket), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Supprimer un ticket",
            description = "Supprime un ticket existant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket supprimé"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Ticket> ticket = ticketService.findById(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ticketService.delete(ticket.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Mettre à jour un ticket",
            description = "Met à jour les informations modifiables d’un ticket ('title', 'description', 'callDuration')")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket mis à jour"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<TicketUpdatedDto> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) TicketUpdatedDto ticketToUpdate) {
       try {
           TicketUpdatedDto ticketSaved = ticketService.update(ticketToUpdate, id);
           return new ResponseEntity<>(ticketSaved, HttpStatus.OK);
       } catch (TicketService.TicketNotFoundException e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
