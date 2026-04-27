package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.flat.ClientDtoFlat;
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
    public List<ClientDtoFlat> findAll() {
        return clientDao.getAllClient();
    }

    @Override
    public Optional<ClientDtoFlat> findDtoById(int id) {
        return clientDao.getClient(id);
    }

    @Override
    public Optional<Client> findById(int id) {
        return clientDao.findById(id);
    }

    @Override
    public Client save(Client client) {
        client.setIdAppUser(null);
        return clientDao.save(client);
    }

    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Override
    public Client update(Client clientToUpdate, int id) throws iClientService.ClientNotFoundException {
        Client currentClient = clientDao.findById(id)
                .orElseThrow(iClientService.ClientNotFoundException::new);

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
