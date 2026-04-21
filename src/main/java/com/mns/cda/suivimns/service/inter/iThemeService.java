package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.Theme;

import java.util.List;
import java.util.Optional;

public interface iThemeService {
    List<Theme> findAll();

    Optional<Theme> findById(int id);

    Optional<Theme> findByDesignation(String designation);

    Theme save(Theme theme);

    void delete(Theme theme);

    Theme update(Theme themeToUpdate, int id) throws ThemeNotFoundException;

    class ThemeNotFoundException extends Exception {
    }
}
