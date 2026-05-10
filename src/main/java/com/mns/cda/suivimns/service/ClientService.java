package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.ClientDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.ClientMapper;
import com.mns.cda.suivimns.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {

    // Classe d'erreur
    public static class ClientNotFoundException extends AppUserService.AppUserNotFoundException {}

    public static class BadPasswordException extends Exception {}


    protected final ClientDao clientDao;
    protected final ClientMapper clientMapper;
    protected final AppUserDao appUserDao;


    public List<ClientDto> findAll() {
        return clientMapper.toDtoList(clientDao.findAll());
    }

    public ClientDto findById(int id) throws ClientService.ClientNotFoundException {
        Client client = clientDao.findById(id)
                .orElseThrow(ClientService.ClientNotFoundException::new);

        return clientMapper.toDto(client);
    }

    public ClientDto save(ClientDto dto) {
        Client client = clientMapper.toEntity(dto);
        client.setIdAppUser(null);
        Client saved = clientDao.save(client);

        return clientMapper.toDto(saved);
    }

    public void delete(int id) throws ClientService.ClientNotFoundException {
        Client client = clientDao.findById(id)
                .orElseThrow(ClientService.ClientNotFoundException::new);

        clientDao.delete(client);
    }

    public ClientDto update(int id, ClientDto dto)
            throws ClientService.ClientNotFoundException, AppUserService.EmailAlreadyUsedException {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        Client currentClient = clientDao.findById(id)
                .orElseThrow(ClientService.ClientNotFoundException::new);

        clientMapper.updateEntityFromDto(dto, currentClient);

        return clientMapper.toDto(clientDao.save(currentClient));
    }

    public void updatePassword(int id, PasswordDto dto)
            throws ClientService.ClientNotFoundException, ClientService.BadPasswordException {

        Client user = clientDao.findById(id)
                .orElseThrow(ClientService.ClientNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new ClientService.BadPasswordException();
        }

        user.setPassword(dto.newPassword());

        clientDao.save(user);
    }
}
