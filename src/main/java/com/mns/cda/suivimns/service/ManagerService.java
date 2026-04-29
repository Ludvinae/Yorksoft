package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dto.ManagerDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.ManagerMapper;
import com.mns.cda.suivimns.model.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ManagerService  {

    // Classe d'erreur
    public static class ManagerNotFoundException extends Exception {}

    public static class BadPasswordException extends Exception {}


    protected final ManagerDao managerDao;
    protected final ManagerMapper managerMapper;
    protected final AppUserDao appUserDao;


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

    public void delete(int id) throws ManagerService.ManagerNotFoundException {
        Manager manager = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        managerDao.delete(manager);
    }

    public ManagerDto update(int id, ManagerDto dto)
            throws ManagerService.ManagerNotFoundException, AppUserService.EmailAlreadyUsedException {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        Manager currentManager = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        managerMapper.updateEntityFromDto(dto, currentManager);

        return managerMapper.toDto(managerDao.save(currentManager));
    }

    public void updatePassword(int id, PasswordDto dto)
            throws ManagerService.ManagerNotFoundException, ManagerService.BadPasswordException {

        Manager user = managerDao.findById(id)
                .orElseThrow(ManagerService.ManagerNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new ManagerService.BadPasswordException();
        }

        user.setPassword(dto.newPassword());

        managerDao.save(user);
    }
}
