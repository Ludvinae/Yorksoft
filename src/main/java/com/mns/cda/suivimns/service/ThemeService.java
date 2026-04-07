package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService {

    public static class ThemeNotFoundException extends Exception {}

    protected final ThemeDao themeDao;

    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    public Optional<Theme> findById(int id) {
        return themeDao.findById(id);
    }

    public void save(Theme theme) {
        theme.setIdTheme(null);
        themeDao.save(theme);
    }

    public void delete(Theme theme) {
        themeDao.delete(theme);
    }

    public void update(Theme themeToUpdate, int id) throws ThemeService.ThemeNotFoundException {
        Optional<Theme> theme = themeDao.findById(id);

        if (theme.isEmpty()) {
            throw new ThemeService.ThemeNotFoundException();
        }

        themeToUpdate.setIdTheme(theme.get().getIdTheme());

        themeDao.save(themeToUpdate);
    }
}
