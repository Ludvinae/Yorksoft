package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.service.inter.iClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService implements iClientService {

    protected final ClientDao clientDao;

    @Override
    public List<ClientDto> findAll() {
        return clientDao.getAllClient();
    }

    @Override
    public ClientDto findDtoById(int id) {
        return clientDao.getClient(id);
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientDao.findById(id);
    }

    @Override
    public void save(Client client) {
        client.setIdAppUser(null);
        clientDao.save(client);
    }

    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Override
    public void update(Client clientToUpdate, int id) throws iClientService.ClientNotFoundException {
        Optional<Client> client = clientDao.findById(id);

        if (client.isEmpty()) {
            throw new iClientService.ClientNotFoundException();
        }

        clientToUpdate.setIdAppUser(client.get().getIdAppUser());
        clientToUpdate.setPassword(client.get().getPassword());

        clientDao.save(clientToUpdate);
    }
}
