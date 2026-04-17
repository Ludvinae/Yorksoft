package com.mns.cda.suivimns.service;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.service.inter.iAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void save(AppUser appUser) {
        appUser.setIdAppUser(null);
        appUserDao.save(appUser);
    }

    @Override
    public void delete(AppUser appUser) {
        appUserDao.delete(appUser);
    }

    @Override
    public void update(AppUser appUserToUpdate, int id) throws iAppUserService.AppUserNotFoundException {
        Optional<AppUser> appUser = appUserDao.findById(id);

        if (appUser.isEmpty()) {
            throw new iAppUserService.AppUserNotFoundException();
        }

        appUserToUpdate.setIdAppUser(appUser.get().getIdAppUser());

        appUserDao.save(appUserToUpdate);
    }
}
