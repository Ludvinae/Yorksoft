package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.AppUserDto;
import com.mns.cda.suivimns.model.AppUser;

import java.util.List;

public interface AppUSerMapper {
    AppUser toDto(AppUser user);

    List<AppUserDto> toDtoList(List<AppUser> userList);

    AppUser toEntity(AppUserDto dto);
}
