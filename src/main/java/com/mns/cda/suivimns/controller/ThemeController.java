package com.mns.cda.suivimns.controller;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.model.groups.OnCreate;
import com.mns.cda.suivimns.model.groups.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ThemeController {

    protected ThemeDao themeDao;

    @Autowired
    public ThemeController(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    @GetMapping("/theme/list")
    public List<Theme> getAll() {
        return themeDao.findAll();
    }

    @GetMapping("/theme/{id}")
    public ResponseEntity<Theme> getById(@PathVariable int id) {

        Optional<Theme> theme = themeDao.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @PostMapping("/theme")
    public ResponseEntity<Theme> create(@RequestBody @Validated(OnCreate.class) Theme theme) {
        theme.setIdTheme(null);
        themeDao.save(theme);

        return new ResponseEntity<>(theme, HttpStatus.CREATED);
    }

    @DeleteMapping("/theme/{id}")
    public ResponseEntity<Theme> delete(@PathVariable int id) {
        Optional<Theme> theme = themeDao.findById(id);
        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        themeDao.delete(theme.get());
        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }

    @PutMapping("/theme/{id}")
    public ResponseEntity<Theme> update(@PathVariable int id, @RequestBody @Validated(OnUpdate.class) Theme themeToUpdate) {
        Optional<Theme> theme = themeDao.findById(id);

        if (theme.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        themeToUpdate.setIdTheme(theme.get().getIdTheme());
        themeDao.save(themeToUpdate);

        return new ResponseEntity<>(theme.get(), HttpStatus.OK);
    }
}
