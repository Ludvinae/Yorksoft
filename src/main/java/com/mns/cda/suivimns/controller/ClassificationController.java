package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Classification;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.model.keys.ClassificationKey;
import com.mns.cda.suivimns.service.inter.iClassificationService;
import com.mns.cda.suivimns.view.TicketView;
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
@CrossOrigin
@RequestMapping("/classification")
@Tag(name = "Classification", description = "Classification des tickets selon les thématiques")
public class ClassificationController {

    protected final iClassificationService classificationService;

    @Operation(summary = "Récupérer toutes les classifications")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    public List<Classification> getAll() {
        return classificationService.findAll();
    }

    @Operation(summary = "Récupérer une classification par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Classification trouvée"),
            @ApiResponse(responseCode = "404", description = "Non trouvée")})
    @GetMapping("/{idTicket}/{idTheme}")
    public ResponseEntity<Classification> getById(@PathVariable int idTicket, @PathVariable int idTheme) {
        ClassificationKey key = new ClassificationKey(idTicket, idTheme);

        Optional<Classification> classification = classificationService.findById(key);
        if (classification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(classification.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer une classification")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Classification> create(@RequestBody @Validated(OnCreate.class) Classification classification) {
        Classification classificationSaved = classificationService.save(classification);

        return new ResponseEntity<>(classificationSaved, HttpStatus.CREATED);
    }


    // Pas de methode Delete et Update ici, on ne change pas l'historisation apres les faits
}
