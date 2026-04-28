package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.dto.VersionDto;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.Version;
import com.mns.cda.suivimns.model.VersionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VersionMapper {

    @Autowired
    protected VersionTypeDao versionTypeDao;

    @Autowired
    protected SoftwareDao softwareDao;

    @Mapping(target="versionType", source="idVersionType")
    @Mapping(target="software", source="idSoftware")
    public abstract Version toEntity(VersionDto dto);

    @Mapping(target = "idVersionType", source = "versionType")
    @Mapping(target = "idSoftware", source = "software")
    public abstract VersionDto toDto(Version version);

    //@Mapping(target = "idVersionType", source = "versionType")
    //@Mapping(target = "idSoftware", source = "software")
    public abstract List<VersionDto> toDtoList(List<Version> versions);

    // Method helper pour ID vers ENTITE
    protected VersionType mapVersionType(Integer id) {
        return versionTypeDao.getReferenceById(id);
    }

    protected Software mapSoftware(Integer id) {
        return softwareDao.getReferenceById(id);
    }


    // Method helper pour ENTITE vers ID
    protected Integer mapVersionType(VersionType versionType) {
        return versionType != null ? versionType.getIdVersionType() : null;
    }

    protected Integer mapSoftware(Software software) {
        return software != null ? software.getIdSoftware() : null;
    }
}
