package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ProcedureDto;
import com.mns.cda.suivimns.exception.ProcedureNotFoundException;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.ProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procedure")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Procedure", description = "Gestion des procedures")
public class ProcedureController {

    protected final ProcedureService procedureService;


    @Operation(summary = "Récupere toutes les procedures",
            description = "Récupere la liste complète de procedure de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsTechnician
    public List<ProcedureDto> getAll() {
        return procedureService.findAll();
    }


    @Operation(summary = "Récupére une procedure en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Procedure trouvée"),
            @ApiResponse(responseCode = "404", description = "Procedure non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ProcedureDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(procedureService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Crée une nouvelle procedure")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Procedure crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsTechnician
    public ResponseEntity<ProcedureDto> create(@RequestBody @Valid ProcedureDto procedure,
                                             @AuthenticationPrincipal AppUserDetails user) {
        return new ResponseEntity<>(procedureService.save(procedure, user), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une procedure selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Procedure effacée"),
            @ApiResponse(responseCode = "404", description = "Procedure non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id, @AuthenticationPrincipal AppUserDetails user) {
        procedureService.delete(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifie une procedure en fonction de son ID",
            description = "Modifie les champs 'subject', 'contenu' d'une procedure")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Procedure modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Procedure non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ProcedureDto> update(@PathVariable int id, @RequestBody @Valid ProcedureDto procedureToUpdate,
                                             @AuthenticationPrincipal AppUserDetails userDetails) {
        return new ResponseEntity<>(procedureService.update(id, procedureToUpdate, userDetails), HttpStatus.OK);
    }
}
