package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.model.Theme;
import com.mns.cda.suivimns.service.inter.iThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService implements iThemeService {

    protected final ThemeDao themeDao;

    @Override
    public List<Theme> findAll() {
        return themeDao.findAll();
    }

    @Override
    public Optional<Theme> findById(int id) {
        return themeDao.findById(id);
    }

    @Override
    public Optional<Theme> findByDesignation(String designation) {
        return themeDao.findByDesignation(designation);
    }

    @Override
    public Theme save(Theme theme) {
        theme.setIdTheme(null);
        return themeDao.save(theme);
    }

    @Override
    public void delete(Theme theme) {
        themeDao.delete(theme);
    }

    @Override
    public void update(Theme themeToUpdate, int id) throws iThemeService.ThemeNotFoundException {
        Optional<Theme> theme = themeDao.findById(id);

        if (theme.isEmpty()) {
            throw new iThemeService.ThemeNotFoundException();
        }

        themeToUpdate.setIdTheme(theme.get().getIdTheme());

        themeDao.save(themeToUpdate);
    }
}
