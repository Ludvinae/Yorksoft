package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {
    
    public static class AppUserNotFoundException extends Exception {}

    protected final AppUserDao appUserDao;

    public List<AppUser> findAll() {
        return appUserDao.findAll();
    }

    public Optional<AppUser> findById(int id) {
        return appUserDao.findById(id);
    }

    public void save(AppUser appUser) {
        appUser.setIdAppUser(null);
        appUserDao.save(appUser);
    }

    public void delete(AppUser appUser) {
        appUserDao.delete(appUser);
    }

    public void update(AppUser appUserToUpdate, int id) throws AppUserService.AppUserNotFoundException {
        Optional<AppUser> appUser = appUserDao.findById(id);

        if (appUser.isEmpty()) {
            throw new AppUserService.AppUserNotFoundException();
        }

        appUserToUpdate.setIdAppUser(appUser.get().getIdAppUser());

        appUserDao.save(appUserToUpdate);
    }
}
