package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.exception.AccountNotOwnedException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.DirectorNotFoundException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.mapper.entity.DirectorMapper;
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

    public List<DirectorDto> findAll() {
        return directorMapper.toDtoList(directorDao.findAll());
    }

    public DirectorDto findById(int id) {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorNotFoundException::new);

        return directorMapper.toDto(director);
    }

    public DirectorDto save(DirectorDto dto) {
        Director director = directorMapper.toEntity(dto);
        director.setIdAppUser(null);
        director.setPhoneNumber(director.getPhoneNumber().trim());

        Director saved = directorDao.save(director);

        return directorMapper.toDto(saved);
    }

    public void insert(Director director) {
        director.setIdAppUser(null);
        director.setPhoneNumber(director.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        director.setPassword(encoder.encode(director.getPassword()));

        appUserDao.save(director);
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

        if (appUserDao.existsByEmail(dto.email())) {
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
