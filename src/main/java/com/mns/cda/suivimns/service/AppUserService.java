package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dto.AppUserDto;
import com.mns.cda.suivimns.dto.PasswordDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.service.inter.iAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements iAppUserService {

    protected final AppUserDao appUserDao;

    @Override
    public List<AppUser> findAll() {
        return appUserDao.findAll();
    }

    @Override
    public Optional<AppUser> findById(int id) {
        return appUserDao.findById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        appUser.setIdAppUser(null);
        return appUserDao.save(appUser);
    }

    @Override
    public void delete(AppUser appUser) {
        appUserDao.delete(appUser);
    }

    @Override
    public AppUser update(AppUserDto dto, int id)
            throws AppUserNotFoundException {

        AppUser user = appUserDao.findById(id)
                .orElseThrow(AppUserNotFoundException::new);

        if (dto.firstName() != null) {
            user.setFirstName(dto.firstName());
        }

        if (dto.lastName() != null) {
            user.setLastName(dto.lastName());
        }

        if (dto.email() != null) {
            // 🔥 idéalement vérifier unicité ici
            user.setEmail(dto.email());
        }

        if (dto.phoneNumber() != null) {
            user.setPhoneNumber(dto.phoneNumber());
        }

        return appUserDao.save(user);
    }

    @Override
    public void updatePassword(int id, PasswordDto dto)
            throws AppUserNotFoundException, BadPasswordException {

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
