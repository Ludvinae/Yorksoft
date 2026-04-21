package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iThemeService;
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
@RequestMapping("/theme")
@RequiredArgsConstructor
@Tag(name = "Theme", description = "Gestion des thématiques des tickets")
public class ThemeController {

    protected final iThemeService themeService;

    @Operation(summary = "Récupérer toutes les thématiques")
    @ApiResponse(responseCode = "200", description = "Liste des thématiques récupérée")
    @GetMapping("/list")
    public List<Theme> getAll() {
        return themeService.findAll();
    }

    @Operation(summary = "Récupérer une thématique par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thématique trouvée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @GetMapping("/{id}")
    public ResponseEntity<Theme> getById(@PathVariable int id) {

        Optional<Theme> theme = themeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer une thématique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Thématique créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Theme> create(@RequestBody @Validated(OnCreate.class) Theme theme) {
        Theme themeSaved = themeService.save(theme);

        return new ResponseEntity<>(themeSaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer une thématique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Thématique supprimée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Theme> theme = themeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        themeService.delete(theme.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Champs modifiables :
     * designation
     * description
     */
    @Operation(
            summary = "Mettre à jour une thématique",
            description = "Modifie la désignation et la description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thématique mise à jour"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Theme> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Theme themeToUpdate) {
        try {
            Theme themeSaved = themeService.update(themeToUpdate, id);
            return new ResponseEntity<>(themeSaved, HttpStatus.OK);
        } catch (iThemeService.ThemeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
