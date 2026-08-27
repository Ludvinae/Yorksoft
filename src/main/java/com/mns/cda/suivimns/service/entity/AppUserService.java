package com.mns.cda.suivimns.service.entity;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.entity.AppUserDto;
import com.mns.cda.suivimns.dto.flat.NewPasswordDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.BadPasswordException;
import com.mns.cda.suivimns.exception.EmailAlreadyUsedException;
import com.mns.cda.suivimns.mapper.entity.AppUserMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {


    protected final AppUserDao appUserDao;
    protected final AppUserMapper appUserMapper;
    protected final PasswordEncoder encoder;
    protected final SecurityService security;

    public List<AppUserDto> findAll() {
        return appUserMapper.toDtoList(appUserDao.findAll());
    }

    public AppUserDto findById(int id) {
        AppUser appUser = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        return appUserMapper.toDto(appUser);
    }

    public AppUserDto save(NewUserDto dto) {
        if (appUserDao.existsByEmail(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        AppUser appUser = appUserMapper.toNewEntity(dto);
        appUser.setIdAppUser(null);
        appUser.setPhoneNumber(appUser.getPhoneNumber().trim());

        // Encodage du password avant de l'inserer en base de données
        appUser.setPassword(encoder.encode(appUser.getPassword()));

        AppUser saved = appUserDao.save(appUser);

        return appUserMapper.toDto(saved);
    }

    public void insert(AppUser appUser) {
        if (appUserDao.existsByEmail(appUser.getEmail())) {
            throw new EmailAlreadyUsedException();
        }

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

    public AppUserDto update(int id, AppUserDto dto, AppUserDetails userDetails) {

        if (appUserDao.existsByEmail(dto.email()) && !userDetails.getEmail().equals(dto.email())) {
            throw new EmailAlreadyUsedException();
        }

        AppUser currentAppUser = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        appUserMapper.updateEntityFromDto(dto, currentAppUser);
        currentAppUser.setPhoneNumber(currentAppUser.getPhoneNumber().trim());

        return appUserMapper.toDto(appUserDao.save(currentAppUser));
    }

    public void updatePassword(int id, NewPasswordDto dto) {

        AppUser user = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        // Changement de mot de passe par un admin : pas besoin de l'ancien mot de passe
        user.setPassword(encoder.encode(dto.newPassword()));

        appUserDao.save(user);
    }

    public void updatePassword(AppUserDetails principal, PasswordDto dto) {

        AppUser user = appUserDao.findById(principal.getId())
                .orElseThrow(AppUserNotFoundException::new);

        // vérifier ancien mot de passe
        if (!encoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new BadPasswordException();
        }

        user.setPassword(encoder.encode(dto.newPassword()));

        appUserDao.save(user);
    }

}
