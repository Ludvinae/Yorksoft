package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.ThemeDao;
import com.mns.cda.suivimns.dto.entity.ThemeDto;
import com.mns.cda.suivimns.exception.ThemeNotFoundException;
import com.mns.cda.suivimns.mapper.entity.ThemeMapper;
import com.mns.cda.suivimns.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    protected final ThemeDao themeDao;
    protected final ThemeMapper themeMapper;

    public List<ThemeDto> findAll() {
        return themeMapper.toDtoList(themeDao.findAll());
    }

    public ThemeDto findById(int id) throws ThemeNotFoundException {
        Theme theme = themeDao.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        return themeMapper.toDto(theme);
    }

    public ThemeDto save(ThemeDto dto) {
        Theme theme = themeMapper.toEntity(dto);
        theme.setIdTheme(null);
        Theme saved = themeDao.save(theme);

        return themeMapper.toDto(saved);
    }

    public void delete(int id) throws ThemeNotFoundException {
        Theme theme = themeDao.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        themeDao.delete(theme);
    }

    public ThemeDto update(int id, ThemeDto themeToUpdate) throws ThemeNotFoundException {

        Theme currentTheme = themeDao.findById(id)
                .orElseThrow(ThemeNotFoundException::new);

        themeMapper.updateEntityFromDto(themeToUpdate, currentTheme);

        return themeMapper.toDto(themeDao.save(currentTheme));
    }
}
