package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.model.Director;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DirectorMapper {
    public abstract DirectorDto toDto(Director director);

    public abstract List<DirectorDto> toDtoList(List<Director> directorList);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Director toEntity(DirectorDto dto);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract Director toNewEntity(NewUserDto dto);

    // Method helper pour Update
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(DirectorDto dto, @MappingTarget Director entity);
}
