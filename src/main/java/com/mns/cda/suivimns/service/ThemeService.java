package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService {

    public static class ThemeNotFoundException extends Exception {
    }

    protected final ThemeDao themeDao;

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public Optional<Theme> findById(int id) {
        return themeDao.findById(id);
    }

    public Optional<Theme> findByDesignation(String designation) {
        return themeDao.findByDesignation(designation);
    }

    public Theme save(Theme theme) {
        theme.setIdTheme(null);
        return themeDao.save(theme);
    }

    public void delete(Theme theme) {
        themeDao.delete(theme);
    }

    public Theme update(Theme themeToUpdate, int id) throws ThemeNotFoundException {
        Theme currentTheme = themeDao.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        currentTheme.setDesignation(themeToUpdate.getDesignation());
        currentTheme.setDescription(themeToUpdate.getDescription());

        return themeDao.save(currentTheme);
    }
}
