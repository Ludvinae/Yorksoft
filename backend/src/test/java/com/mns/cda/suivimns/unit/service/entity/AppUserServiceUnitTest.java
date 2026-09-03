package com.mns.cda.suivimns.unit.service.entity;

import com.mns.cda.suivimns.dao.AdminDao;
import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.exception.AppUserNotFoundException;
import com.mns.cda.suivimns.exception.LastAdminException;
import com.mns.cda.suivimns.exception.SelfDeletionException;
import com.mns.cda.suivimns.mapper.entity.AppUserMapper;
import com.mns.cda.suivimns.model.Admin;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.AppUserService;
import com.mns.cda.suivimns.service.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceUnitTest {

    @Mock
    private AppUserDao appUserDao;

    @Mock
    private AdminDao adminDao;

    @Mock
    private AppUserMapper appUserMapper;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private SecurityService security;

    @InjectMocks
    private AppUserService appUserService;

    private AppUserDetails principalOf(int id) {
        Admin admin = new Admin();
        admin.setIdAppUser(id);
        admin.setEmail("admin" + id + "@test.fr");
        return new AppUserDetails(admin);
    }

    // =========================
    // AUTO-SUPPRESSION
    // =========================
    @Test
    void shouldThrowWhenAdminDeletesOwnAccount() {

        AppUserDetails principal = principalOf(1);

        assertThrows(SelfDeletionException.class,
                () -> appUserService.delete(1, principal));

        // On ne doit meme pas aller chercher la ressource en base
        verify(appUserDao, never()).findById(anyInt());
        verify(appUserDao, never()).delete(any(AppUser.class));
    }

    // =========================
    // DERNIER ADMIN
    // =========================
    @Test
    void shouldThrowWhenDeletingLastAdmin() {

        Admin target = new Admin();
        target.setIdAppUser(2);
        target.setEmail("target@test.fr");

        AppUserDetails principal = principalOf(1);

        when(appUserDao.findById(2)).thenReturn(Optional.of(target));
        when(adminDao.count()).thenReturn(1L);

        assertThrows(LastAdminException.class,
                () -> appUserService.delete(2, principal));

        verify(appUserDao, never()).delete(any(AppUser.class));
    }

    @Test
    void shouldDeleteAdminWhenNotLastOne() {

        Admin target = new Admin();
        target.setIdAppUser(2);
        target.setEmail("target@test.fr");

        AppUserDetails principal = principalOf(1);

        when(appUserDao.findById(2)).thenReturn(Optional.of(target));
        when(adminDao.count()).thenReturn(2L);

        appUserService.delete(2, principal);

        verify(appUserDao).delete(target);
    }

    // =========================
    // SUPPRESSION NORMALE
    // =========================
    @Test
    void shouldDeleteOtherUser() {

        Technician target = new Technician();
        target.setIdAppUser(2);
        target.setEmail("target@test.fr");

        AppUserDetails principal = principalOf(1);

        when(appUserDao.findById(2)).thenReturn(Optional.of(target));

        appUserService.delete(2, principal);

        verify(appUserDao).delete(target);
        // La verification du nombre d'admins ne concerne que les comptes admin
        verify(adminDao, never()).count();
    }

    @Test
    void shouldThrowWhenUserNotFound() {

        AppUserDetails principal = principalOf(1);

        when(appUserDao.findById(2)).thenReturn(Optional.empty());

        assertThrows(AppUserNotFoundException.class,
                () -> appUserService.delete(2, principal));

        verify(appUserDao, never()).delete(any(AppUser.class));
    }
}
