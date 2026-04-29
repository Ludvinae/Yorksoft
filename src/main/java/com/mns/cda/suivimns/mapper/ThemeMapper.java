package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ThemeDto;
import com.mns.cda.suivimns.model.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ThemeMapper {
    public abstract ThemeDto toDto(Theme theme);

    public abstract List<ThemeDto> toDtoList(List<Theme> themeList);

    public abstract Theme toEntity(ThemeDto dto);

    // Method helper pour Update
    @Mapping(target = "idTheme", ignore = true)
    public abstract void updateEntityFromDto(ThemeDto dto, @MappingTarget Theme entity);
}
