package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ClientDao;
import com.mns.cda.suivimns.dto.entity.ClientDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.dto.search.ClientListDto;
import com.mns.cda.suivimns.dto.search.ClientSearchCriteria;
import com.mns.cda.suivimns.exception.*;
import com.mns.cda.suivimns.mapper.entity.ClientMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.search.ClientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientService {


    protected final ClientDao clientDao;
    protected final ClientMapper clientMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;
    protected final ClientQueryService queryService;

    public List<ClientDto> findAll() {
        return clientMapper.toDtoList(clientDao.findAll());
    }

    public ClientDto findById(int id) {
        Client client = clientDao.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        return clientMapper.toDto(client);
    }

    public ClientDto save(ClientDto dto) {
        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        Client client = clientMapper.toEntity(dto);
        client.setIdAppUser(null);
        client.setPhoneNumber(client.getPhoneNumber().trim());
        Client saved = clientDao.save(client);

        return clientMapper.toDto(saved);
    }

    public void insert(Client client) {
        if (appUserDao.existsByEmail(client.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        client.setIdAppUser(null);
        client.setPhoneNumber(client.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        client.setPassword(encoder.encode(client.getPassword()));

        appUserDao.save(client);
    }

    public void delete(int id, AppUserDetails userDetails) {
        Client client = clientDao.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        clientDao.delete(client);
    }

    public ClientDto update(int id, ClientDto dto, AppUserDetails userDetails) {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        Client currentClient = clientDao.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        clientMapper.updateEntityFromDto(dto, currentClient);
        currentClient.setPhoneNumber(currentClient.getPhoneNumber().trim());

        return clientMapper.toDto(clientDao.save(currentClient));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails) {

        Client user = clientDao.findById(id)
                .orElseThrow(ClientNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        clientDao.save(user);
    }

    public Page<ClientListDto> search(ClientSearchCriteria criteria, Pageable pageable) {
        return queryService.search(criteria, pageable);
    }
}
