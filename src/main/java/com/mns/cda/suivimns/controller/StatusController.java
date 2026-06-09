package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.StatusDto;
import com.mns.cda.suivimns.enumerate.StatusEnum;
import com.mns.cda.suivimns.exception.StatusNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.StatusService;
import com.mns.cda.suivimns.service.workflow.StatusTransition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/status")
@RequiredArgsConstructor
@Tag(name = "Statut", description = "Gestion des statuts des tickets")
public class StatusController {

    protected final StatusService statusService;
    protected final StatusTransition transition;

    @Operation(summary = "Récupère toutes les statuts",
            description = "Récupère la liste complète de statut de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsTechnician
    public List<StatusDto> getAll() {
        return statusService.findAll();
    }


    @Operation(summary = "Récupère les statuts vers lesquels il est possible de transitionner à partir d'un statut donné")
    @ApiResponse(responseCode = "200", description = "Set récupérée avec succès")
    @GetMapping("/allowed-transitions/{status}")
    @IsTechnician
    public Set<StatusEnum> getPossibleTransitions(@PathVariable StatusEnum status) {
        return transition.getAllowedTransitions(status);
    }


    @Operation(summary = "Récupère une statut en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Statut trouvée"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<StatusDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(statusService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Crée une nouvelle statut")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Statut crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsManager
    public ResponseEntity<StatusDto> create(@RequestBody @Valid StatusDto status) {
        return new ResponseEntity<>(statusService.save(status), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une statut selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Statut effacée"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        statusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifie une statut en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'statusList' d'une statut")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Statut modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Statut non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsManager
    public ResponseEntity<StatusDto> update(@PathVariable int id, @RequestBody @Valid StatusDto statusToUpdate) {
        return new ResponseEntity<>(statusService.update(id, statusToUpdate), HttpStatus.OK);
    }
}
