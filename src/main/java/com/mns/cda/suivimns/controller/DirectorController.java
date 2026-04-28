package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.service.DirectorService;
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
@RequiredArgsConstructor
@RequestMapping("/director")
@Tag(name="Directeur", description = "Gère les directeurs")
public class DirectorController {

    protected final DirectorService directorService;


    @Operation(summary = "Récupère tous les directeurs")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    public List<Director> getAll() {
        return directorService.findAll();
    }


    @Operation(summary = "Récupère un directeur en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Directeur récupéré"),
                    @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Director> getById(@PathVariable int id) {

        Optional<Director> director = directorService.findById(id);
        if (director.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(director.get(), HttpStatus.OK);
    }


    @Operation(summary = "Crée un nouveau directeur")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Directeur crée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Director> create(@RequestBody @Validated(OnCreate.class) Director director) {
        Director directorSaved = directorService.save(director);

        return new ResponseEntity<>(directorSaved, HttpStatus.CREATED);
    }


    @Operation(summary = "Efface un directeur")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Directeur crée"),
                    @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Director> director = directorService.findById(id);
        if (director.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        directorService.delete(director.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<Director> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Director directorToUpdate) {
        try {
            Director directorSaved = directorService.update(directorToUpdate, id);
            return new ResponseEntity<>(directorSaved, HttpStatus.OK);
        } catch (iDirectorService.DirectorNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */
}
