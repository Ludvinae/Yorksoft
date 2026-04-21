package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;

import java.util.List;
import java.util.Optional;

public interface iClientService {

    ClientDto toDto(Client client);

    List<ClientDto> findAll();

    Optional<ClientDto> findDtoById(int id);

    Optional<Client> findById(int id);

    Client save(Client client);

    void delete(Client client);

    void update(Client clientToUpdate, int id) throws ClientNotFoundException;

    class ClientNotFoundException extends Exception {
    }
}
