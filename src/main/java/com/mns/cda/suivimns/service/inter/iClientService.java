package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.flat.ClientDtoFlat;
import com.mns.cda.suivimns.model.Client;

import java.util.List;
import java.util.Optional;

public interface iClientService {

    ClientDtoFlat toDto(Client client);

    List<ClientDtoFlat> findAll();

    Optional<ClientDtoFlat> findDtoById(int id);

    Optional<Client> findById(int id);

    Client save(Client client);

    void delete(Client client);

    Client update(Client clientToUpdate, int id) throws ClientNotFoundException;

    class ClientNotFoundException extends Exception {
    }
}
