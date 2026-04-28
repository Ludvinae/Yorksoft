package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.SoftwareTypeDao;
import com.mns.cda.suivimns.dto.SoftwareDto;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import com.mns.cda.suivimns.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SoftwareMapper {

    @Autowired
    protected SoftwareTypeDao softwareTypeDao;

    @Mapping(target = "idSoftwareType", source = "type")
    public abstract SoftwareDto toDto(Software software);

    //@Mapping(target = "idSoftwareType", source = "softwareType")
    public abstract List<SoftwareDto> toDtoList(List<Software> software);

    @Mapping(target="type", source="idSoftwareType")
    public abstract Software toEntity(SoftwareDto dto);


    // Method helper pour ID vers ENTITE
    protected SoftwareType mapIdToSoftwareType(Integer id) {
        return softwareTypeDao.getReferenceById(id);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapSoftwareTypeToId(SoftwareType softwareType) {
        return softwareType != null ? softwareType.getIdSoftwareType() : null;
    }

    // Method helper pour Update
    @Mapping(target = "idSoftware", ignore = true)
    @Mapping(target = "type", source = "idSoftwareType")
    public abstract void updateEntityFromDto(SoftwareDto dto, @MappingTarget Software entity);
}
