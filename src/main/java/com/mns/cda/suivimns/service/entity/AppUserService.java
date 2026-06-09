package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dto.entity.AppUserDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.mapper.entity.AppUserMapper;
import com.mns.cda.suivimns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppUserService {


    protected final AppUserDao appUserDao;
    protected final AppUserMapper appUserMapper;
    protected final PasswordEncoder encoder;

    public List<AppUserDto> findAll() {
        return appUserMapper.toDtoList(appUserDao.findAll());
    }

    public AppUserDto findById(int id) {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        return appUserMapper.toDto(appUser);
    }

    public AppUserDto save(AppUserDto dto) {
        AppUser appUser = appUserMapper.toEntity(dto);
        appUser.setIdAppUser(null);
        appUser.setPhoneNumber(appUser.getPhoneNumber().trim());

        AppUser saved = appUserDao.save(appUser);

        return appUserMapper.toDto(saved);
    }

    public void insert(AppUser appUser) {
        appUser.setIdAppUser(null);
        appUser.setPhoneNumber(appUser.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        appUser.setPassword(encoder.encode(appUser.getPassword()));

        appUserDao.save(appUser);
    }

    public void delete(int id) {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        appUserDao.delete(appUser);
    }

    public AppUserDto update(int id, AppUserDto dto) {

        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        AppUser currentAppUser = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        appUserMapper.updateEntityFromDto(dto, currentAppUser);
        currentAppUser.setPhoneNumber(currentAppUser.getPhoneNumber().trim());

        return appUserMapper.toDto(appUserDao.save(currentAppUser));
    }

    public void updatePassword(int id, PasswordDto dto) {

        AppUser user = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        // vérifier ancien mot de passe
        if (!Objects.equals(user.getPassword(), dto.oldPassword())) {
            throw new BadPasswordException();
        }

        user.setPassword(dto.newPassword());

        appUserDao.save(user);
    }

}
