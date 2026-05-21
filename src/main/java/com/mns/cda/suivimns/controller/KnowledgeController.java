package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.KnowledgeDto;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.KnowledgeService;
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
@RequestMapping("/knowledge")
@RequiredArgsConstructor
@Tag(name="Connaissance", description = "Gère une connaissance sur une thématique et des knowledges de logiciels")
public class KnowledgeController {

    protected final KnowledgeService knowledgeService;


    @Operation(summary = "Récupere toutes les connaissances",
                description = "Récupere la liste complète de connaissance de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsTechnician
    public List<KnowledgeDto> getAll() {
        return knowledgeService.findAll();
    }


    @Operation(summary = "Récupére une connaissance en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Connaissance trouvée"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<KnowledgeDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(knowledgeService.findById(id) , HttpStatus.OK);
        } catch (KnowledgeService.KnowledgeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle connaissance")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Connaissance crée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsTechnician
    public ResponseEntity<KnowledgeDto> create(@RequestBody @Valid KnowledgeDto knowledge) {
        return new ResponseEntity<>(knowledgeService.save(knowledge), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une connaissance selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Connaissance effacée"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            knowledgeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (KnowledgeService.KnowledgeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une connaissance en fonction de son ID",
                description = "Modifie les champs 'subject', 'theme' et 'knowledgeList' d'une connaissance")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Connaissance modifiée avec succés"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsTechnician
    public ResponseEntity<KnowledgeDto> update(@PathVariable int id, @RequestBody @Valid KnowledgeDto knowledgeToUpdate) {
        try {
            return new ResponseEntity<>(knowledgeService.update(id, knowledgeToUpdate), HttpStatus.OK);
        } catch (KnowledgeService.KnowledgeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
