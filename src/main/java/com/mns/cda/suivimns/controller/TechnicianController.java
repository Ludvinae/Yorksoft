package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.TechnicianService;
import com.mns.cda.suivimns.service.inter.iTechnicianService;
import com.mns.cda.suivimns.view.TechnicianView;
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
@RequestMapping("/technician")
@Tag(name="Technicien", description = "Gère les techniciens")
public class TechnicianController {

    protected final iTechnicianService technicianService;


    @Operation(summary = "Récupère tous les techniciens")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    public List<Technician> getAll() {
        return technicianService.findAll();
    }


    @Operation(summary = "Récupère un technicien en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Technicien récupéré"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Technician> getById(@PathVariable int id) {

        Optional<Technician> technician = technicianService.findById(id);
        if (technician.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(technician.get(), HttpStatus.OK);
    }


    @Operation(summary = "Crée un nouveau technicien")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Technicien crée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Technician> create(@RequestBody @Validated(OnCreate.class) Technician technician) {
        Technician technicianSaved = technicianService.save(technician);

        return new ResponseEntity<>(technicianSaved, HttpStatus.CREATED);
    }


    @Operation(summary = "Efface un technicien")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Technicien crée"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Technician> technician = technicianService.findById(id);
        if (technician.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        technicianService.delete(technician.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*
    @PutMapping("/{id}")
    public ResponseEntity<Technician> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Technician technicianToUpdate){
        try {
            Technician technicianSaved = technicianService.update(technicianToUpdate, id);
            return new ResponseEntity<>(technicianSaved, HttpStatus.OK);
        } catch (iTechnicianService.TechnicianNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */
}
