package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Article;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iArticleService;
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
@RequestMapping("/article")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Article", description = "Gestion des articles")
public class ArticleController {

    protected final iArticleService articleService;

    @Operation(summary = "Récupérer tous les articles")
    @ApiResponse(responseCode = "200", description = "Liste des articles récupérée avec succès")
    @GetMapping("/list")
    public List<Article> getAll() {
        return articleService.findAll();
    }

    @Operation(summary = "Récupérer un article par son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article trouvé"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable int id) {
        Optional<Article> article = articleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(article.get(), HttpStatus.OK);
    }

    @Operation(summary = "Créer un nouvel article")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Article créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Article> create(@RequestBody @Validated(OnCreate.class) Article article) {

        Article articleSaved = articleService.save(article);

        return new ResponseEntity<>(articleSaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un article")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Article supprimé"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Article> article = articleService.findById(id);

        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        articleService.delete(article.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Champs modifiables :
     * content
     */
    @Operation(summary = "Mettre à jour un article")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article mis à jour"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    public ResponseEntity<Article> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Article articleToUpdate) {

        try {
            Article articleSaved = articleService.update(articleToUpdate, id);
            return new ResponseEntity<>(articleSaved, HttpStatus.OK);
        } catch (iArticleService.ArticleNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
