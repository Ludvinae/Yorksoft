package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dto.entity.ManagerDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.entity.ManagerMapper;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ManagerService  {

    // Classe d'erreur
    public static class ManagerNotFoundException extends AppUserService.AppUserNotFoundException {}

    public static class BadPasswordException extends Exception {}


    protected final ManagerDao managerDao;
    protected final ManagerMapper managerMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;

    public List<ManagerDto> findAll() {
        return managerMapper.toDtoList(managerDao.findAll());
    }

    public ManagerDto findById(int id) throws ManagerService.ManagerNotFoundException {
        Manager manager = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        return managerMapper.toDto(manager);
    }

    public ManagerDto save(ManagerDto dto) {
        Manager manager = managerMapper.toEntity(dto);
        manager.setIdAppUser(null);
        Manager saved = managerDao.save(manager);

        return managerMapper.toDto(saved);
    }

    public void insert(Manager manager) {
        manager.setIdAppUser(null);

        // Encodage du password avant de l'inserer en base de données
        manager.setPassword(encoder.encode(manager.getPassword()));

        appUserDao.save(manager);
    }

    public void delete(int id, AppUserDetails userDetails) throws ManagerService.ManagerNotFoundException, AppUserService.AccountNotOwnedException {
        Manager manager = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        managerDao.delete(manager);
    }

    public ManagerDto update(int id, ManagerDto dto, AppUserDetails userDetails)
            throws ManagerService.ManagerNotFoundException, AppUserService.EmailAlreadyUsedException, AppUserService.AccountNotOwnedException {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        Manager currentManager = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        managerMapper.updateEntityFromDto(dto, currentManager);

        return managerMapper.toDto(managerDao.save(currentManager));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails)
            throws ManagerService.ManagerNotFoundException, ManagerService.BadPasswordException, AppUserService.AccountNotOwnedException {

        Manager user = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new ManagerService.BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        managerDao.save(user);
    }
}
