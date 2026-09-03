package com.mns.cda.suivimns.mapper.entity;

import com.mns.cda.suivimns.dto.entity.ClientDto;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.TicketListDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.License;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {
    public abstract ClientDto toDto(Client client);

    public abstract List<ClientDto> toDtoList(List<Client> clientList);

    public abstract Client toEntity(ClientDto dto);

    // Pagination and filters
    @Mapping(source = "licenseList", target = "softwareIdsList")
    public abstract ClientListDto toListDto(Client client);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(ClientDto dto, @MappingTarget Client entity);

    // MEthod helper pour la liste de software d'un client
    protected List<Integer> map(List<License> licenses) {

        if (licenses == null) {
            return List.of();
        }

        return licenses.stream()
                .map(License::getSoftware)
                .filter(Objects::nonNull)
                .map(Software::getIdSoftware)
                .distinct().toList();
    }
}

