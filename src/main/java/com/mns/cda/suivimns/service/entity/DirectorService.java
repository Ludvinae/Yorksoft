package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.enumerate.ActivityType;
import com.mns.cda.suivimns.exception.*;
import com.mns.cda.suivimns.mapper.entity.DirectorMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DirectorService  {




    protected final DirectorDao directorDao;
    protected final DirectorMapper directorMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;
    protected final ActivityService activityService;

    public List<DirectorDto> findAll() {
        return directorMapper.toDtoList(directorDao.findAll());
    }

    public DirectorDto findById(int id) {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorNotFoundException::new);

        return directorMapper.toDto(director);
    }

    public DirectorDto save(DirectorDto dto) {
        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        Director director = directorMapper.toEntity(dto);
        director.setIdAppUser(null);
        director.setPhoneNumber(director.getPhoneNumber().trim());

        Director saved = directorDao.save(director);

        return directorMapper.toDto(saved);
    }

    public void insert(Director director, AppUserDetails principal) {
        if (appUserDao.existsByEmail(director.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

        director.setIdAppUser(null);
        director.setPhoneNumber(director.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        director.setPassword(encoder.encode(director.getPassword()));

        appUserDao.save(director);

        AppUser admin = appUserDao.findById(principal.getId()).orElseThrow(AppUserNotFoundException::new);
        activityService.log(admin, "A créé un compte directeur pour " +
                director.getFirstName() + " " + director.getLastName(), ActivityType.USER);
    }

    public void delete(int id, AppUserDetails userDetails) {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        directorDao.delete(director);
    }

    public DirectorDto update(int id, DirectorDto dto, AppUserDetails userDetails) {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        Director currentDirector = directorDao.findById(id)
                .orElseThrow(DirectorNotFoundException::new);

        directorMapper.updateEntityFromDto(dto, currentDirector);
        currentDirector.setPhoneNumber(currentDirector.getPhoneNumber().trim());

        return directorMapper.toDto(directorDao.save(currentDirector));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails) {

        Director user = directorDao.findById(id)
                .orElseThrow(DirectorNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        directorDao.save(user);
    }
}
