package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.AssignmentDto;
import com.mns.cda.suivimns.security.IsDirector;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.AssignmentService;
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

@RestController
@CrossOrigin
@RequestMapping("/assignment")
@RequiredArgsConstructor
@Tag(name = "Affectation", description = "Affectation des tickets à un technicien par un manager")
public class AssignmentController {

    protected final AssignmentService assignmentService;

    @Operation(summary = "Récupere toutes les attributions",
            description = "Récupere la liste complète de attribution de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsManager
    public List<AssignmentDto> getAll() {
        return assignmentService.findAll();
    }


    @Operation(summary = "Récupére une attribution en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Attribution trouvée"),
            @ApiResponse(responseCode = "404", description = "Attribution non trouvée")})
    @GetMapping("/{id}")
    @IsManager
    public ResponseEntity<AssignmentDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(assignmentService.findById(id) , HttpStatus.OK);
        } catch (AssignmentService.AssignmentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* Fait dans le controller du ticket
    @Operation(summary = "Crée une nouvelle attribution")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Attribution crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<AssignmentDto> create(@RequestBody @Valid AssignmentDto assignment) {
        return new ResponseEntity<>(assignmentService.save(assignment), HttpStatus.CREATED);
    }

     */


    // Pas de route DELETE et PUT
}
