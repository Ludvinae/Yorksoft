package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ImpactDto;
import com.mns.cda.suivimns.exception.ImpactNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.ImpactService;
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
@RequiredArgsConstructor
@RequestMapping("/impact")
@Tag(name = "Impact", description = "Gestion des niveaux d'impact sur la priorité des tickets")
public class ImpactController {

    protected final ImpactService impactService;
    @Operation(summary = "Récupere toutes les impacts",
            description = "Récupere la liste complète de impact de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsTechnician
    public List<ImpactDto> getAll() {
        return impactService.findAll();
    }


    @Operation(summary = "Récupére une impact en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Impact trouvée"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ImpactDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(impactService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Crée une nouvelle impact")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Impact crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsManager
    public ResponseEntity<ImpactDto> create(@RequestBody @Valid ImpactDto impact) {
        return new ResponseEntity<>(impactService.save(impact), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une impact selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Impact effacée"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        impactService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifie une impact en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'impactList' d'une impact")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Impact modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Impact non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsManager
    public ResponseEntity<ImpactDto> update(@PathVariable int id, @RequestBody @Valid ImpactDto impactToUpdate) {
        return new ResponseEntity<>(impactService.update(id, impactToUpdate), HttpStatus.OK);
    }
}
