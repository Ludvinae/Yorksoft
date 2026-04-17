package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface iAppUserService {
    List<AppUser> findAll();

    Optional<AppUser> findById(int id);

    void save(AppUser appUser);

    void delete(AppUser appUser);

    void update(AppUser appUserToUpdate, int id) throws AppUserNotFoundException;

    public static class AppUserNotFoundException extends Exception {
    }
}
