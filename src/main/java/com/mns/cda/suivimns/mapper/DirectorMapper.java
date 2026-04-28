package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.DirectorDto;
import com.mns.cda.suivimns.model.Director;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    DirectorDto toDto(Director director);

    List<DirectorDto> toDtoList(List<Director> directorList);

    Director toEntity(DirectorDto dto);
}
