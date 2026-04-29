package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.mapper.ThemeMapper;
import com.mns.cda.suivimns.model.Theme;
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
    protected final ThemeMapper themeMapper;

    public List<ThemeDto> findAll() {
        return themeMapper.toDtoList(themeDao.findAll());
    }

    public ThemeDto findById(int id) throws ThemeService.ThemeNotFoundException {
        Theme theme = themeDao.findById(id)
                .orElseThrow(ThemeService.ThemeNotFoundException::new);

        return themeMapper.toDto(theme);
    }

    public ThemeDto save(ThemeDto dto) {
        Theme theme = themeMapper.toEntity(dto);
        theme.setIdTheme(null);
        Theme saved = themeDao.save(theme);

        return themeMapper.toDto(saved);
    }

    public void delete(int id) throws ThemeService.ThemeNotFoundException {
        Theme theme = themeDao.findById(id)
                .orElseThrow(ThemeService.ThemeNotFoundException::new);

        themeDao.delete(theme);
    }

    public ThemeDto update(int id, ThemeDto themeToUpdate) throws ThemeService.ThemeNotFoundException {

        Theme currentTheme = themeDao.findById(id)
                .orElseThrow(ThemeService.ThemeNotFoundException::new);

        themeMapper.updateEntityFromDto(themeToUpdate, currentTheme);

        return themeMapper.toDto(themeDao.save(currentTheme));
    }
}
