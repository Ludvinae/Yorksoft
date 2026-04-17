package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iSoftwareTypeService;
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
@RequestMapping("/software-type")
public class SoftwareTypeController {

    protected final iSoftwareTypeService iSoftwareTypeService;

    @GetMapping("/list")
    public List<SoftwareType> findAll() {
        return iSoftwareTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareType> findById(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = iSoftwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(softwareType.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SoftwareType> create(@RequestBody @Validated(OnCreate.class) SoftwareType typeToInsert) {

        typeToInsert.setIdSoftwareType(null);
        iSoftwareTypeService.save(typeToInsert);

        return new ResponseEntity<>(typeToInsert, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SoftwareType> delete(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = iSoftwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iSoftwareTypeService.delete(softwareType.get());

        return new ResponseEntity<>(softwareType.get(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) SoftwareType typeToUpdate) {
        Optional<SoftwareType> softwareType = iSoftwareTypeService.findById(id);
        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        typeToUpdate.setIdSoftwareType(id);
        iSoftwareTypeService.save(typeToUpdate);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
