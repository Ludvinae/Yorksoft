package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.service.inter.iClientService;

import java.util.List;
import java.util.Optional;

public class MockClientService implements iClientService {
    @Override
    public List<ClientDto> findAll() {
        return List.of();
    }

    @Override
    public ClientDto findDtoById(int id) {
        return null;
    }

    @Override
    public Optional<Client> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Client client) {

    }

    @Override
    public void delete(Client client) {

    }

    @Override
    public void update(Client clientToUpdate, int id) throws ClientNotFoundException {

    }
}
