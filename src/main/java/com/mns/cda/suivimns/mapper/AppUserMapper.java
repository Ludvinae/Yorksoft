package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.AppUserDto;
import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AppUserMapper {
    public abstract AppUser toDto(AppUser user);

    public abstract List<AppUserDto> toDtoList(List<AppUser> userList);

    public abstract AppUser toEntity(AppUserDto dto);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    public abstract void updateEntityFromDto(AppUserDto dto, @MappingTarget AppUser entity);
}
