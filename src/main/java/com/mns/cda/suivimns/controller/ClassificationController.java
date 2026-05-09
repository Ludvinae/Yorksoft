package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.ClassificationDto;
import com.mns.cda.suivimns.service.ClassificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/classification")
@Tag(name = "Classification", description = "Classification des tickets selon les thématiques")
public class ClassificationController {

    protected final ClassificationService classificationService;

    @Operation(summary = "Récupere toutes les classifications",
            description = "Récupere la liste complète de classification de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<ClassificationDto> getAll() {
        return classificationService.findAll();
    }


    @Operation(summary = "Récupére une classification en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Classification trouvée"),
            @ApiResponse(responseCode = "404", description = "Classification non trouvée")})
    @GetMapping("/{idClassification}")
    public ResponseEntity<ClassificationDto> getById(@PathVariable int idClassification) {

        try {
            return new ResponseEntity<>(classificationService.findById(idClassification) , HttpStatus.OK);
        } catch (ClassificationService.ClassificationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Asignation d'une thematique a un ticket fait par le ticket directement ?
    /*
    @Operation(summary = "Crée une nouvelle classification")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Classification crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ClassificationDto> create(@RequestBody @Valid ClassificationDto classification) {
        return new ResponseEntity<>(classificationService.save(classification), HttpStatus.CREATED);
    }

     */


    // Pas de methode Delete et Update ici, on ne change pas l'historisation apres les faits
}
