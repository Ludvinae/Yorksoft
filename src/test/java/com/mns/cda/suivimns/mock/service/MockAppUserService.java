package com.mns.cda.suivimns.mock.service;

import com.mns.cda.suivimns.dto.flat.AppUserDto;
import com.mns.cda.suivimns.dto.flat.PasswordDto;
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
    public AppUser save(AppUser appUser) {
        return null;
    }


    @Override
    public void delete(AppUser appUser) {

    }

    @Override
    public AppUser update(AppUserDto appUserToUpdate, int id) throws AppUserNotFoundException {
        return null;
    }

    @Override
    public void updatePassword(int id, PasswordDto dto) throws AppUserNotFoundException, BadPasswordException {

    }
}
