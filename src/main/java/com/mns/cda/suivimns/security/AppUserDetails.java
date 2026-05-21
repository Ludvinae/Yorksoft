package com.mns.cda.suivimns.security;

import com.mns.cda.suivimns.model.*;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class AppUserDetails implements UserDetails {

    // Voir page 447 du support de cours pour adapter avec l'heritage a la place des roles

    protected Client client;
    protected Technician technician;
    protected Manager manager;
    protected Director director;
    protected Admin admin;

    protected String email;
    protected String password;
    protected String name;
    protected int id;
    protected String rank = "";


    public AppUserDetails(Client client) {
        this.client = client;
        this.email = client.getEmail();
        this.password = client.getPassword();
        this.name = client.getFirstName() + " " + client.getLastName();
        this.id = client.getIdAppUser();
    }

    public AppUserDetails(Technician technician) {
        this.technician = technician;
        this.email = technician.getEmail();
        this.password = technician.getPassword();
        this.name = technician.getFirstName() + " " + technician.getLastName();
        this.id = technician.getIdAppUser();
        this.rank = "N" + technician.getRank().toString();
    }

    public AppUserDetails(Manager manager) {
        this.manager = manager;
        this.email = manager.getEmail();
        this.password = manager.getPassword();
        this.name = manager.getFirstName() + " " + manager.getLastName();
        this.id = manager.getIdAppUser();
    }

    public AppUserDetails(Director director) {
        this.director = director;
        this.email = director.getEmail();
        this.password = director.getPassword();
        this.name = director.getFirstName() + " " + director.getLastName();
        this.id = director.getIdAppUser();
    }

    public AppUserDetails(Admin admin) {
        this.admin = admin;
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.name = admin.getFirstName() + " " + admin.getLastName();
        this.id = admin.getIdAppUser();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(
                client != null
                    ? "ROLE_CLIENT"
                    : technician != null
                        ? "ROLE_TECHNICIAN"
                        : manager != null
                            ? "ROLE_MANAGER"
                            : director != null
                                ? "ROLE_DIRECTOR"
                                : admin != null
                                    ? "ROLE_ADMIN"
                                    :  null
        ));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
