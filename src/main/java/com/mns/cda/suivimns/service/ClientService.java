package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    public static class ClientNotFoundException extends Exception {}

    protected final ClientDao clientDao;

    public List<ClientDto> findAll() {
        return clientDao.getAllClient();
    }

    public ClientDto findDtoById(int id) {
        return clientDao.getClient(id);
    }

    public Optional<Client> findById(int id) {
        return clientDao.findById(id);
    }

    public void save(Client client) {
        client.setIdAppUser(null);
        clientDao.save(client);
    }

    public void delete(Client client) {
        clientDao.delete(client);
    }

    public void update(Client clientToUpdate, int id) throws ClientService.ClientNotFoundException {
        Optional<Client> client = clientDao.findById(id);

        if (client.isEmpty()) {
            throw new ClientService.ClientNotFoundException();
        }

        clientToUpdate.setIdAppUser(client.get().getIdAppUser());
        clientToUpdate.setPassword(client.get().getPassword());

        clientDao.save(clientToUpdate);
    }
}
