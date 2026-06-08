package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.details.TicketDetailFullDto;
import com.mns.cda.suivimns.dto.entity.TicketDto;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.dto.search.TicketSearchCriteria;
import com.mns.cda.suivimns.dto.workflow.*;
import com.mns.cda.suivimns.exception.InvalidSortCriteriaException;
import com.mns.cda.suivimns.security.*;
import com.mns.cda.suivimns.service.business.TicketDetailService;
import com.mns.cda.suivimns.service.entity.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@CrossOrigin
@Tag(name = "Ticket", description = "Gestion des tickets de support (création, suivi, affectation, résolution)")
public class TicketController {

    protected final TicketService ticketService;
    protected final TicketDetailService ticketDetailService;


    @Operation(summary = "Récupère tous les tickets",
            description = "Récupère la liste paginée de ticket, peut être triée et filtrée")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Critère de tri invalide"),
            @ApiResponse(responseCode = "403", description = "Utilisateur non autorisé"),
            @ApiResponse(responseCode = "500", description = "Role inconnu")})
    @GetMapping("/list")
    @IsUser
    public ResponseEntity<Page<TicketListDto>> getAllPageable(
            TicketSearchCriteria criteria,
            @PageableDefault(size = 15, sort = "openDate", direction = Sort.Direction.DESC)
            Pageable pageable,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        return new ResponseEntity<>(ticketService.getAllPageable(criteria, pageable, principal) , HttpStatus.OK);
    }


    @Operation(summary = "Récupère une ticket en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket trouvée"),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé"),
            @ApiResponse(responseCode = "403", description = "Pas autorisé à consulter ce ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée"),
            @ApiResponse(responseCode = "500", description = "Role inconnu")})
    @GetMapping("/{id}")
    @IsEmployee
    public ResponseEntity<TicketDto> getById(@PathVariable int id) {

        return new ResponseEntity<>(ticketService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Récupére les détails d'un ticket en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket trouvée"),
            @ApiResponse(responseCode = "403", description = "Pas autorisé à consulter ce ticket"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée"),
            @ApiResponse(responseCode = "500", description = "Role inconnu")})
    @GetMapping("/{id}/detail")
    @IsUser
    public ResponseEntity<TicketDetailFullDto> getDetails(@PathVariable int id,
                                                          @AuthenticationPrincipal AppUserDetails principal) {

        return new ResponseEntity<>(ticketDetailService.getTicketDetails(id, principal) , HttpStatus.OK);
    }


    @Operation(summary = "Crée un nouveau ticket")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Ticket crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")})
    @PostMapping
    @IsTechnician
    public ResponseEntity<TicketDto> create(@RequestBody @Valid TicketCreationDto ticket,
                                            @AuthenticationPrincipal AppUserDetails principal) {
        return new ResponseEntity<>(ticketService.save(ticket, principal), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une ticket selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Ticket effacée"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {

        ticketService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Modifie la description et la solution d'un ticket",
            description = "Modifie les champs 'description' et 'solution' d'un ticket")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket modifiée avec succès"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Refusé: provoquerait une incoherence d'état"),})
    @PatchMapping("/{id}/description")
    @IsTechnician
    public ResponseEntity<TicketDetailFullDto> updateDescription(@PathVariable int id, @RequestBody @Valid TicketDescriptionDto ticketToUpdate,
                                            @AuthenticationPrincipal AppUserDetails principal) {

        return new ResponseEntity<>(ticketService.update(id, ticketToUpdate, principal), HttpStatus.OK);
    }


    // Workflow
    @Operation(summary = "Assigne un ticket à un technicien par un manager")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Ticket attribué"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource inexistante"),
            @ApiResponse(responseCode = "409", description = "Ticket déja attribué à ce technicien")})
    @PostMapping("/{id}/assign")
    @IsManager
    public ResponseEntity<TicketDto> assign(@PathVariable Integer id,
                                            @RequestBody @Valid TicketAssignmentDto assignment,
                                            @AuthenticationPrincipal AppUserDetails principal) {
        return new ResponseEntity<>(ticketService.assignTicket(id, assignment, principal), HttpStatus.OK);
    }


    @Operation(summary = "Clôture un ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket clôturé"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket ou utilisateur introuvable"),
            @ApiResponse(responseCode = "409", description = "Transition interdite")
    })
    @PostMapping("/{id}/close")
    @IsTechnician
    public TicketDto closeTicket(
            @PathVariable Integer id,
            @RequestBody StateChangeJustification justification,
            @AuthenticationPrincipal AppUserDetails principal
            ) {

        return ticketService.closeTicket(id, justification, principal);
    }


    @Operation(summary = "Prends en charge un ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket pris en charge"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket ou technicien introuvable"),
            @ApiResponse(responseCode = "409", description = "Transition interdite")
    })
    @PostMapping("/{id}/start-progress")
    @IsTechnician
    public TicketDto startProgress(
            @PathVariable Integer id,
            @RequestBody StateChangeJustification justification,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        return ticketService.takeTicketInCharge(id, justification, principal);
    }


    @Operation(summary = "Reprends la prise en charge d'un ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket repris en charge"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket ou technicien introuvable"),
            @ApiResponse(responseCode = "409", description = "Transition interdite")
    })
    @PostMapping("/{id}/resume-progress")
    @IsTechnician
    public TicketDto resumeProgress(
            @PathVariable Integer id,
            @RequestBody StateChangeJustification justification,
            @AuthenticationPrincipal AppUserDetails principal
    ) {
        return ticketService.resumeTicket(id, justification, principal);
    }


    @Operation(summary = "Propose une solution pour le ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solution proposée"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket ou technicien introuvable"),
            @ApiResponse(responseCode = "409", description = "Transition interdite")
    })
    @PostMapping("/{id}/solve")
    @IsTechnician
    public TicketDto solveTicket(
            @PathVariable Integer id,
            @RequestBody @Valid StateChangeJustification justification,
            @AuthenticationPrincipal AppUserDetails principal
    ) {

        return ticketService.solveTicket(id, justification, principal);
    }


    @Operation(summary = "Met le ticket en attente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket mis en attente"),
            @ApiResponse(responseCode = "400", description = "Statut non reconnu"),
            @ApiResponse(responseCode = "403", description = "Autorisations insuffisantes"),
            @ApiResponse(responseCode = "404", description = "Ticket ou technicien introuvable"),
            @ApiResponse(responseCode = "409", description = "Transition interdite")
    })
    @PostMapping("/{id}/wait")
    @IsTechnician
    public TicketDto setWaitingStatus(
            @PathVariable Integer id,
            @RequestBody @Valid TicketWaitDto dto,
            @AuthenticationPrincipal AppUserDetails principal
    ) {

        return ticketService.setWaitingStatus(id, dto, principal);
    }

}
