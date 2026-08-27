package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.ManagerDao;
import com.mns.cda.suivimns.dto.entity.ManagerDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.exception.AccountNotOwnedException;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.exception.ManagerNotFoundException;
import com.mns.cda.suivimns.mapper.entity.ManagerMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.security.AppUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ManagerService  {


    protected final ManagerDao managerDao;
    protected final ManagerMapper managerMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;
    protected final ActivityService activityService;

    public List<ManagerDto> findAll() {
        return managerMapper.toDtoList(managerDao.findAll());
    }

    public ManagerDto findById(int id) {
        Manager manager = managerDao.findById(id)
                .orElseThrow(ManagerNotFoundException::new);

        return managerMapper.toDto(manager);
    }

    public ManagerDto save(ManagerDto dto) {
        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        Manager manager = managerMapper.toEntity(dto);
        manager.setIdAppUser(null);
        Manager saved = managerDao.save(manager);

        return managerMapper.toDto(saved);
    }

    @Transactional
    public void insert(Manager manager, AppUserDetails principal) {
        if (appUserDao.existsByEmail(manager.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        manager.setIdAppUser(null);

        // Encodage du password avant de l'inserer en base de données
        manager.setPassword(encoder.encode(manager.getPassword()));

        appUserDao.save(manager);

        AppUser admin = appUserDao.findById(principal.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(admin, "A créé un compte manager pour " +
                manager.getFirstName() + " " + manager.getLastName(), ActivityType.USER);
    }

    public void delete(int id, AppUserDetails userDetails) {
        Manager manager = managerDao.findById(id)
                .orElseThrow(ManagerNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        managerDao.delete(manager);
    }

    public ManagerDto update(int id, ManagerDto dto, AppUserDetails userDetails) {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        Manager currentManager = managerDao.findById(id)
                .orElseThrow(ManagerNotFoundException::new);

        managerMapper.updateEntityFromDto(dto, currentManager);

        return managerMapper.toDto(managerDao.save(currentManager));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails) {

        Manager user = managerDao.findById(id)
                .orElseThrow(ManagerNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        managerDao.save(user);
    }
}
