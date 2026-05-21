package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dto.entity.CommentDto;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.security.IsEmployee;
import com.mns.cda.suivimns.security.IsTechnician;
import com.mns.cda.suivimns.service.entity.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name="Commentaires", description="Gestion des commentaires attachés à un ticket")
public class CommentController {

    protected final CommentService commentService;

    @Operation(summary = "Récupere toutes les commentaires",
            description = "Récupere la liste complète de commentaire de la base")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succés")
    @GetMapping("/list")
    @IsEmployee
    public List<CommentDto> getAll() {
        return commentService.findAll();
    }


    @Operation(summary = "Récupére une commentaire en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Commentaire trouvée"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvée")})
    @GetMapping("/{id}")
    @IsEmployee
    public ResponseEntity<CommentDto> getById(@PathVariable int id) {

        try {
            return new ResponseEntity<>(commentService.findById(id) , HttpStatus.OK);
        } catch (CommentService.CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crée une nouvelle commentaire")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Commentaire crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    @IsEmployee
    public ResponseEntity<CommentDto> create(@RequestBody @Valid CommentDto comment) {
        return new ResponseEntity<>(commentService.save(comment), HttpStatus.CREATED);
    }


    @Operation(summary = "Efface une commentaire selon son ID")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Commentaire effacée"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvée")})
    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            commentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CommentService.CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modifie une commentaire en fonction de son ID",
            description = "Modifie les champs 'subject', 'theme' et 'commentList' d'une commentaire")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Commentaire modifiée avec succés"),
            @ApiResponse(responseCode = "404", description = "Commentaire non trouvée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<CommentDto> update(@PathVariable int id, @RequestBody @Valid CommentDto commentToUpdate) {
        try {
            return new ResponseEntity<>(commentService.update(id, commentToUpdate), HttpStatus.OK);
        } catch (CommentService.CommentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
