package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.dto.LicenseDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.Software;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LicenseMapper {

    @Autowired
    protected SoftwareDao softwareDao;

    @Autowired
    protected ClientDao clientDao;

    @Mapping(target = "idSoftware", source = "software")
    @Mapping(target = "idClient", source = "client")
    public abstract LicenseDto toDto(License license);

    //@Mapping(target = "idLicenseType", source = "licenseType")
    public abstract List<LicenseDto> toDtoList(List<License> license);

    @Mapping(target="software", source="idSoftware")
    @Mapping(target="client", source="idClient")
    public abstract License toEntity(LicenseDto dto);


    // Method helper pour ID vers ENTITE
    protected Software mapIdToSoftware(Integer id) {
        return softwareDao.getReferenceById(id);
    }

    protected Client mapIdToClient(Integer id) {
        return clientDao.getReferenceById(id);
    }

    // Method helper pour ENTITE vers ID
    protected Integer mapSoftwareToId(Software software) {
        return software != null ? software.getIdSoftware() : null;
    }

    protected Integer mapClientToId(Client client) {
        return client != null ? client.getIdAppUser() : null;
    }
}

