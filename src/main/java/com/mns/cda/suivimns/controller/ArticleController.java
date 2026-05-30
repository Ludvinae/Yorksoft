package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.ArticleDto;
import com.mns.cda.suivimns.exception.ArticleNotFoundException;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.ArticleService;
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
@RequestMapping("/article")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Article", description = "Gestion des articles")
public class ArticleController {

    protected final ArticleService articleService;


    @Operation(summary = "Récupere toutes les articles",
            description = "Récupere la liste complète de article de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsTechnician
    public List<ArticleDto> getAll() {
        return articleService.findAll();
    }


    @Operation(summary = "Récupére une article en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Article trouvée"),
            @ApiResponse(responseCode = "404", description = "Article non trouvée")})
    @GetMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ArticleDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(articleService.findById(id) , HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle article")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Article crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsTechnician
    public ResponseEntity<ArticleDto> create(@RequestBody @Valid ArticleDto article) {
        return new ResponseEntity<>(articleService.save(article), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une article selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Article effacée"),
            @ApiResponse(responseCode = "404", description = "Article non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            articleService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une article en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'articleList' d'une article")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Article modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Article non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsTechnician
    public ResponseEntity<ArticleDto> update(@PathVariable int id, @RequestBody @Valid ArticleDto articleToUpdate,
                                             @AuthenticationPrincipal AppUserDetails userDetails) {
        try {
            return new ResponseEntity<>(articleService.update(id, articleToUpdate, userDetails), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
