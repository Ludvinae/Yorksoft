package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
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

    protected final iSoftwareTypeService softwareTypeService;

    @GetMapping("/list")
    public List<SoftwareType> findAll() {
        return softwareTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareType> findById(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = softwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(softwareType.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SoftwareType> create(@RequestBody @Validated(OnCreate.class) SoftwareType typeToInsert) {

        SoftwareType typeSaved = softwareTypeService.save(typeToInsert);

        return new ResponseEntity<>(typeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        Optional<SoftwareType> softwareType = softwareTypeService.findById(id);

        if (softwareType.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        softwareTypeService.delete(softwareType.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* Champs modifiables :
     * designation
     */
    @PutMapping("/{id}")
    public ResponseEntity<SoftwareType> update(@PathVariable Integer id, @RequestBody @Validated(OnUpdate.class) SoftwareType typeToUpdate) {
        try {
            SoftwareType softwareTypeSaved = softwareTypeService.update(typeToUpdate, id);
            return new ResponseEntity<>(softwareTypeSaved, HttpStatus.OK);
        } catch (iSoftwareTypeService.SoftwareTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
