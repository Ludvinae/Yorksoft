package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
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

    protected final iThemeService iThemeService;

    @GetMapping("/list")
    public List<Theme> getAll() {
        return iThemeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> getById(@PathVariable int id) {

        Optional<Theme> theme = iThemeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Theme> create(@RequestBody @Validated(OnCreate.class) Theme theme) {
        theme.setIdTheme(null);
        iThemeService.save(theme);

        return new ResponseEntity<>(theme, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Theme> delete(@PathVariable int id) {
        Optional<Theme> theme = iThemeService.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        iThemeService.delete(theme.get());
        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theme> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Theme themeToUpdate) {
        Optional<Theme> theme = iThemeService.findById(id);

        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        themeToUpdate.setIdTheme(theme.get().getIdTheme());
        iThemeService.save(themeToUpdate);

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }
}
