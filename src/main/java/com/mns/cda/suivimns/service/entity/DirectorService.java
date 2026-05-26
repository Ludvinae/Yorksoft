package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.dto.entity.DirectorDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.entity.DirectorMapper;
import com.mns.cda.suivimns.model.Client;
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


    // Classe d'erreur
    public static class DirectorNotFoundException extends AppUserService.AppUserNotFoundException {}

    public static class BadPasswordException extends Exception {}


    protected final DirectorDao directorDao;
    protected final DirectorMapper directorMapper;
    protected final AppUserDao appUserDao;
    protected final PasswordEncoder encoder;

    public List<DirectorDto> findAll() {
        return directorMapper.toDtoList(directorDao.findAll());
    }

    public DirectorDto findById(int id) throws DirectorService.DirectorNotFoundException {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

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

    public void delete(int id, AppUserDetails userDetails) throws DirectorService.DirectorNotFoundException, AppUserService.AccountNotOwnedException {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        directorDao.delete(director);
    }

    public DirectorDto update(int id, DirectorDto dto, AppUserDetails userDetails)
            throws DirectorService.DirectorNotFoundException, AppUserService.EmailAlreadyUsedException, AppUserService.AccountNotOwnedException {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        Director currentDirector = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        directorMapper.updateEntityFromDto(dto, currentDirector);
        currentDirector.setPhoneNumber(currentDirector.getPhoneNumber().trim());

        return directorMapper.toDto(directorDao.save(currentDirector));
    }

    public void updatePassword(int id, PasswordDto dto, AppUserDetails userDetails)
            throws DirectorService.DirectorNotFoundException, DirectorService.BadPasswordException, AppUserService.AccountNotOwnedException {

        Director user = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new DirectorService.BadPasswordException();
        }

        if (!Objects.equals(userDetails.getUserRole(), "ADMIN") &&
                userDetails.getId() != id) {
            throw new AppUserService.AccountNotOwnedException();
        }

        user.setPassword(dto.newPassword());

        directorDao.save(user);
    }
}
