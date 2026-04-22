package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Impact;
import com.mns.cda.suivimns.model.Knowledge;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iImpactService;
import com.mns.cda.suivimns.service.inter.iKnowledgeService;
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
@CrossOrigin
@RequestMapping("/knowledge")
@RequiredArgsConstructor
@Tag(name="Connaissance", description = "Gère une connaissance sur une thématique et des versions de logiciels")
public class KnowledgeController {

    protected final iKnowledgeService knowledgeService;

    @Operation(summary = "Récupere toutes les connaissances",
                description = "Récupere la liste complète de connaissance de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<Knowledge> getAll() {
        return knowledgeService.findAll();
    }

    @Operation(summary = "Récupére une connaissance en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Connaissance trouvée"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<Knowledge> getById(@PathVariable int id) {

        Optional<Knowledge> knowledge = knowledgeService.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(knowledge.get(), HttpStatus.OK);
    }

    @Operation(summary = "Crée une nouvelle connaissance")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Connaissance crée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Knowledge> create(@RequestBody @Validated(OnCreate.class) Knowledge knowledge) {
        Knowledge knowledgeSaved = knowledgeService.save(knowledge);

        return new ResponseEntity<>(knowledgeSaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Efface une connaissance selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Connaissance effacée"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Knowledge> knowledge = knowledgeService.findById(id);
        if (knowledge.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        knowledgeService.delete(knowledge.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Modifie une connaissance en fonction de son ID",
                description = "Modifie les champs 'subject', 'theme' et 'versionList' d'une connaissance")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Connaissance modifiée avec succés"),
                    @ApiResponse(responseCode = "404", description = "Connaissance non trouvée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Knowledge> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Knowledge knowledgeToUpdate) {
        try {
            Knowledge knowledgeSaved = knowledgeService.update(knowledgeToUpdate, id);
            return new ResponseEntity<>(knowledgeSaved, HttpStatus.OK);
        } catch (iKnowledgeService.KnowledgeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
