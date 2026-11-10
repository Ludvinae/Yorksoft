package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.AppUserDto;
import com.mns.cda.suivimns.model.AppUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class AppUserMapper {
    public abstract AppUserDto toDto(AppUser user);

    public abstract List<AppUserDto> toDtoList(List<AppUser> userList);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract AppUser toEntity(AppUserDto dto);

    // Only technicians have a rank, a base AppUser has none
    @Mapping(target = "rank", ignore = true)
    public abstract NewUserDto toNewDto(AppUser user);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public abstract AppUser toNewEntity(NewUserDto dto);

    // Method helper pour Update
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(AppUserDto dto, @MappingTarget AppUser entity);
}
