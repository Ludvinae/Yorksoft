package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.flat.ClientDtoFlat;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.service.inter.iClientService;

import java.util.List;
import java.util.Optional;

public class MockClientService implements iClientService {
    @Override
    public ClientDtoFlat toDto(Client client) {
        return null;
    }

    @Override
    public List<ClientDtoFlat> findAll() {
        return List.of();
    }

    @Override
    public Optional<ClientDtoFlat> findDtoById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public void delete(Client client) {

    }

    @Override
    public Client update(Client clientToUpdate, int id) throws ClientNotFoundException {
        return null;
    }
}
