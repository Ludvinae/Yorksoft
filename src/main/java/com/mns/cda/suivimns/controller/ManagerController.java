package com.mns.cda.suivimns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iManagerService;
import com.mns.cda.suivimns.view.ManagerView;
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
@RequestMapping("/manager")
@Tag(name="Manager", description = "Gère les managers")
public class ManagerController {

    protected final iManagerService managerService;

    @Operation(summary = "Récupère tous les managers")
    @ApiResponse(responseCode = "200", description = "Liste récupérée")
    @GetMapping("/list")
    @JsonView(ManagerView.class)
    public List<Manager> getAll() {
        return managerService.findAll();
    }

    @Operation(summary = "Récupère un manager en fonction de son ID")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Manager récupéré"),
            @ApiResponse(responseCode = "404", description = "Non trouvé")})
    @GetMapping("/{id}")
    public ResponseEntity<Manager> getById(@PathVariable int id) {

        Optional<Manager> manager = managerService.findById(id);
        if (manager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(manager.get(), HttpStatus.OK);
    }

    @Operation(summary = "Crée un manager")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Manager crée"),
                    @ApiResponse(responseCode = "400", description = "Données invalides")})
    @PostMapping
    public ResponseEntity<Manager> create(@RequestBody @Validated(OnCreate.class) Manager manager) {
        Manager managerSaved = managerService.save(manager);

        return new ResponseEntity<>(managerSaved, HttpStatus.CREATED);
    }

    @Operation(summary = "Efface un manager")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Manager effacé"),
                    @ApiResponse(responseCode = "404", description = "Identifiant incorrect")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Manager> manager = managerService.findById(id);
        if (manager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        managerService.delete(manager.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<Manager> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Manager managerToUpdate){
        try {
            Manager managerSaved = managerService.update(managerToUpdate, id);
            return new ResponseEntity<>(managerSaved, HttpStatus.OK);
        } catch (iManagerService.ManagerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     */
}
