package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {
    public abstract ClientDto toDto(Client client);

    public abstract List<ClientDto> toDtoList(List<Client> clientList);

    public abstract Client toEntity(ClientDto dto);

    // Method helper pour Update
    @Mapping(target = "idAppUser", ignore = true)
    @Mapping(target= "password", ignore = true)
    public abstract void updateEntityFromDto(ClientDto dto, @MappingTarget Client entity);
}

