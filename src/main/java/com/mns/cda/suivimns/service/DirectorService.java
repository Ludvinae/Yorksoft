package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.DirectorDao;
import com.mns.cda.suivimns.dto.DirectorDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.mapper.DirectorMapper;
import com.mns.cda.suivimns.model.Director;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DirectorService  {


    // Classe d'erreur
    public static class DirectorNotFoundException extends Exception {}

    public static class BadPasswordException extends Exception {}


    protected final DirectorDao directorDao;
    protected final DirectorMapper directorMapper;
    protected final AppUserDao appUserDao;


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
        Director saved = directorDao.save(director);

        return directorMapper.toDto(saved);
    }

    public void delete(int id) throws DirectorService.DirectorNotFoundException {
        Director director = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        directorDao.delete(director);
    }

    public DirectorDto update(int id, DirectorDto dto)
            throws DirectorService.DirectorNotFoundException, AppUserService.EmailAlreadyUsedException {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new AppUserService.EmailAlreadyUsedException();
        }

        Director currentDirector = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        directorMapper.updateEntityFromDto(dto, currentDirector);

        return directorMapper.toDto(directorDao.save(currentDirector));
    }

    public void updatePassword(int id, PasswordDto dto)
            throws DirectorService.DirectorNotFoundException, DirectorService.BadPasswordException {

        Director user = directorDao.findById(id)
                .orElseThrow(DirectorService.DirectorNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new DirectorService.BadPasswordException();
        }

        user.setPassword(dto.newPassword());

        directorDao.save(user);
    }
}
