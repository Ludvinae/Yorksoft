package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ThemeDto;
import com.mns.cda.suivimns.exception.ThemeNotFoundException;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsManager;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.ThemeService;
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
    @IsTechnician
    public List<ThemeDto> getAll() {
        return themeService.findAll();
    }


    @Operation(summary = "Récupére une thématique en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Thématique trouvée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ThemeDto> getById(@PathVariable int id) {
        return new ResponseEntity<>(themeService.findById(id) , HttpStatus.OK);
    }


    @Operation(summary = "Crée une nouvelle thématique")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Thématique crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsManager
    public ResponseEntity<ThemeDto> create(@RequestBody @Valid ThemeDto theme) {
        return new ResponseEntity<>(themeService.save(theme), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une thématique selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Thématique effacée"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        themeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifie une thématique en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'themeList' d'une thématique")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Thématique modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Thématique non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsManager
    public ResponseEntity<ThemeDto> update(@PathVariable int id, @RequestBody @Valid ThemeDto themeToUpdate) {
        return new ResponseEntity<>(themeService.update(id, themeToUpdate), HttpStatus.OK);
    }
}
