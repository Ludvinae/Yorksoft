package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Comment;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iAppUserService;
import com.mns.cda.suivimns.service.inter.iCommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name="Commentaires", description="Gestion des commentaires attachés à un ticket")
public class CommentController {

    protected final iCommentService commentService;


    @Operation(summary="Récuperer tous les commentaires")
    @ApiResponse(responseCode = "200", description="Liste récupérée")
    @GetMapping("/list")
    public List<Comment> getAll() {
        return commentService.findAll();
    }


    @Operation(summary="Récupere un commentaire en fonction de son ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description="Commentaire récupéré"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getById(@PathVariable int id) {

        Optional<Comment> comment = commentService.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comment.get(), HttpStatus.OK);
    }


    @Operation(summary="Crée un nouveau commentaire")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Crée"),
            @ApiResponse(responseCode = "400", description = "Erreur validation")})
    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody @Validated(OnCreate.class) Comment comment) {
        Comment commentSaved = commentService.save(comment);

        return new ResponseEntity<>(commentSaved, HttpStatus.CREATED);
    }


    @Operation(summary = "Efface un commentaire")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Commentaire supprimé"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Comment> comment = commentService.findById(id);
        if (comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commentService.delete(comment.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(summary = "Modifier un commentaire",
                description = "Met à jour le champ 'content")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Commentaire modifié avec succés"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Comment commentToUpdate) {
        try {
            Comment commentSaved = commentService.update(commentToUpdate, id);
            return new ResponseEntity<>(commentSaved, HttpStatus.OK);
        } catch (iCommentService.CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
