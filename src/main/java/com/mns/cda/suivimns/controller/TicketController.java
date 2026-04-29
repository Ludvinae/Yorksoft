package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.TicketDto;
import com.mns.cda.suivimns.dto.flat.TicketCreation;
import com.mns.cda.suivimns.dto.flat.TicketFullWithLatest;
import com.mns.cda.suivimns.dto.flat.TicketResponse;
import com.mns.cda.suivimns.model.Ticket;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@CrossOrigin
@Tag(name = "Ticket", description = "Gestion des tickets de support (création, suivi, affectation, résolution)")
public class TicketController {

    protected final TicketService ticketService;


    @Operation(summary = "Récupere toutes les tickets",
            description = "Récupere la liste complète de ticket de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<TicketDto> getAll() {
        return ticketService.findAll();
    }


    @Operation(summary = "Récupére une ticket en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket trouvée"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(ticketService.findById(id) , HttpStatus.OK);
        } catch (TicketService.TicketNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle ticket")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Ticket crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<TicketDto> create(@RequestBody @Valid TicketDto ticket) {
        return new ResponseEntity<>(ticketService.save(ticket), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une ticket selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Ticket effacée"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            ticketService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (TicketService.TicketNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une ticket en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'ticketList' d'une ticket")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> update(@PathVariable int id, @RequestBody @Valid TicketDto ticketToUpdate) {
        try {
            return new ResponseEntity<>(ticketService.update(id, ticketToUpdate), HttpStatus.OK);
        } catch (TicketService.TicketNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /// ////////

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


}
