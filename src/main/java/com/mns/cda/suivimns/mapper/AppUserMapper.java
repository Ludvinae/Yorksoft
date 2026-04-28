package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.AppUserDto;
import com.mns.cda.suivimns.model.AppUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUser toDto(AppUser user);

    List<AppUserDto> toDtoList(List<AppUser> userList);

    AppUser toEntity(AppUserDto dto);
}
