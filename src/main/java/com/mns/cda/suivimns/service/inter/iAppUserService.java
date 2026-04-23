package com.mns.cda.suivimns.service.inter;

import com.mns.cda.suivimns.dto.AppUserDto;
import com.mns.cda.suivimns.dto.PasswordDto;
import com.mns.cda.suivimns.model.AppUser;

import java.util.List;
import java.util.Optional;

public interface iAppUserService {
    List<AppUser> findAll();

    Optional<AppUser> findById(int id);

    AppUser save(AppUser appUser);

    void delete(AppUser appUser);

    AppUser update(AppUserDto appUserToUpdate, int id) throws AppUserNotFoundException, EmailAlreadyUsedException;

    void updatePassword(int id, PasswordDto dto) throws AppUserNotFoundException, BadPasswordException;

    class AppUserNotFoundException extends Exception {
    }

    class EmailAlreadyUsedException extends Exception {}

    class BadPasswordException extends Exception {}
}
