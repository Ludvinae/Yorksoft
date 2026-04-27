package com.mns.cda.suivimns.mapper;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;

import java.util.List;

public interface ClientMapper {
    ClientDto toDto(Client client);

    List<ClientDto> toDtoList(List<Client> clientList);

    Client toEntity(ClientDto dto);
}

