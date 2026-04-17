package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.VersionType;
import com.mns.cda.suivimns.service.inter.iVersionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/version-type")
@RequiredArgsConstructor
public class VersionTypeController {

    private final iVersionTypeService iVersionTypeService;

    @GetMapping("/list")
    public List<VersionType> getAll() {
        return iVersionTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionType> getById(@PathVariable Integer id) {
        Optional<VersionType> versionType = iVersionTypeService.findById(id);

        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(versionType.get() , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VersionType> create(@RequestBody @Validated() VersionType versionType) {
        versionType.setIdVersionType(null);

        iVersionTypeService.save(versionType);
        return new ResponseEntity<>(versionType , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VersionType> delete(@PathVariable Integer id) {
        Optional<VersionType> versionType = iVersionTypeService.findById(id);
        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iVersionTypeService.delete(versionType.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VersionType> update(@PathVariable Integer id, @RequestBody @Validated() VersionType typeToUpdate) {
        Optional<VersionType> versionType = iVersionTypeService.findById(id);

        if (versionType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        typeToUpdate.setIdVersionType(id);
        iVersionTypeService.save(typeToUpdate);

        return new ResponseEntity<>(typeToUpdate, HttpStatus.OK);
    }
}
