package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.model.Theme;

import java.util.List;

public interface ThemeMapper {
    ThemeDto toDto(Theme theme);

    List<ThemeDto> toDtoList(List<Theme> themeList);

    Theme toEntity(ThemeDto dto);
}
