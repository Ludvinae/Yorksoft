package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.service.ThemeService;
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
@RequestMapping("/theme")
@RequiredArgsConstructor
@Tag(name = "Thématique", description = "Gestion des thématiques des tickets")
public class ThemeController {

    protected final ThemeService themeService;
    @Operation(summary = "Récupere toutes les thématiques",
            description = "Récupere la liste complète de thématique de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    public List<ThemeDto> getAll() {
        return themeService.findAll();
    }


    @Operation(summary = "Récupére une thématique en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Thématique trouvée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(themeService.findById(id) , HttpStatus.OK);
        } catch (ThemeService.ThemeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle thématique")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Thématique crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<ThemeDto> create(@RequestBody @Valid ThemeDto theme) {
        return new ResponseEntity<>(themeService.save(theme), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une thématique selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Thématique effacée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            themeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ThemeService.ThemeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une thématique en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'themeList' d'une thématique")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Thématique modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDto> update(@PathVariable int id, @RequestBody @Valid ThemeDto themeToUpdate) {
        try {
            return new ResponseEntity<>(themeService.update(id, themeToUpdate), HttpStatus.OK);
        } catch (ThemeService.ThemeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
