package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.entity.ThemeDto;
import com.mns.cda.suivimns.model.Theme;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ThemeMapper {
    public abstract ThemeDto toDto(Theme theme);

    public abstract List<ThemeDto> toDtoList(List<Theme> themeList);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Theme toEntity(ThemeDto dto);

    // Method helper pour Update
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "idTheme", ignore = true)
    public abstract void updateEntityFromDto(ThemeDto dto, @MappingTarget Theme entity);
}
