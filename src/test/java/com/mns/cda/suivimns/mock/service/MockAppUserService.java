package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.service.inter.iAppUserService;

import java.util.List;
import java.util.Optional;

public class MockAppUserService implements iAppUserService {
    @Override
    public List<AppUser> findAll() {
        return List.of();
    }

    @Override
    public Optional<AppUser> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(AppUser appUser) {

    }

    @Override
    public void delete(AppUser appUser) {

    }

    @Override
    public void update(AppUser appUserToUpdate, int id) throws AppUserNotFoundException {

    }
}
