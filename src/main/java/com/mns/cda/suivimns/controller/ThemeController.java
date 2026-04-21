package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Status;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import com.mns.cda.suivimns.service.inter.iLicenseService;
import com.mns.cda.suivimns.service.inter.iThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {

    protected final iThemeService themeService;

    @GetMapping("/list")
    public List<Theme> getAll() {
        return themeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> getById(@PathVariable int id) {

        Optional<Theme> theme = themeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Theme> create(@RequestBody @Validated(OnCreate.class) Theme theme) {
        Theme themeSaved = themeService.save(theme);

        return new ResponseEntity<>(themeSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Theme> theme = themeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        themeService.delete(theme.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theme> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Theme themeToUpdate) {
        try {
            Theme themeSaved = themeService.update(themeToUpdate, id);
            return new ResponseEntity<>(themeSaved, HttpStatus.OK);
        } catch (iThemeService.ThemeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
