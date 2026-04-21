package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Assignment;
import com.mns.cda.suivimns.service.inter.iArticleService;
import com.mns.cda.suivimns.service.inter.iAssignmentService;
import com.mns.cda.suivimns.view.AssignmentView;
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
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/assignment")
@RequiredArgsConstructor
@Tag(name = "Assignment", description = "Affectation des tickets à un technicien par un manager")
public class AssignmentController {

    protected final iAssignmentService iAssignmentService;

    @Operation(summary = "Récupérer toutes les affectations")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    @JsonView(AssignmentView.class)
    public List<Assignment> getAll() {
        return iAssignmentService.findAll();
    }

    @Operation(summary = "Récupérer une affectation par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assignment trouvé"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @GetMapping("/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Assignment> getById(@PathVariable int id) {
        Optional<Assignment> assignment = iAssignmentService.findById(id);
        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(assignment.get(), HttpStatus.OK);
    }

    @Operation(summary = "Affecter un ticket à un technicien")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Erreur validation")})
    @PostMapping
    public ResponseEntity<Assignment> create(@RequestBody @Valid Assignment assignment) {

        iAssignmentService.firstSave(assignment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignment);
    }

    @Operation(summary = "Supprimer une affectation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Assignment> assignment = iAssignmentService.findById(id);

        if (assignment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iAssignmentService.delete(assignment.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Mettre à jour une affectation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mis à jour"),
            @ApiResponse(responseCode = "404", description = "Non trouvé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")})
    @PutMapping("/{id}")
    @JsonView(AssignmentView.class)
    public ResponseEntity<Void> update(@PathVariable int id, @RequestBody @Valid Assignment assignmentToUpdate){

        try {
            iAssignmentService.update(assignmentToUpdate, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (iAssignmentService.AssignmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (iAssignmentService.AssignmentBadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
