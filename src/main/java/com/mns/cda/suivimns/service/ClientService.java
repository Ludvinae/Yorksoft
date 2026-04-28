package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.flat.ClientDtoFlat;
import com.mns.cda.suivimns.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    public static class ClientNotFoundException extends Exception {
    }

    protected final ClientDao clientDao;


    public List<ClientDtoFlat> findAll() {
        return clientDao.getAllClient();
    }


    public Optional<ClientDtoFlat> findDtoById(int id) {
        return clientDao.getClient(id);
    }


    public Optional<Client> findById(int id) {
        return clientDao.findById(id);
    }


    public Client save(Client client) {
        client.setIdAppUser(null);
        return clientDao.save(client);
    }


    public void delete(Client client) {
        clientDao.delete(client);
    }


    public Client update(Client clientToUpdate, int id) throws ClientNotFoundException {
        Client currentClient = clientDao.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        currentClient.setImportance(clientToUpdate.getImportance());

        return clientDao.save(currentClient);
    }

    public ClientDtoFlat toDto(Client client) {
        return new ClientDtoFlat(
                client.getIdAppUser(),
                client.getFirstName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getImportance()
        );
    }
}
