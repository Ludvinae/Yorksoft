package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.inter.iThemeService;

import java.util.List;
import java.util.Optional;

public class MockThemeService implements iThemeService {
    @Override
    public List<Theme> findAll() {
        return List.of();
    }

    @Override
    public Optional<Theme> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Theme> findByDesignation(String designation) {
        return Optional.empty();
    }

    @Override
    public void save(Theme theme) {

    }

    @Override
    public void delete(Theme theme) {

    }

    @Override
    public void update(Theme themeToUpdate, int id) throws ThemeNotFoundException {

    }
}
