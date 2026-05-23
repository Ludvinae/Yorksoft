package com.mns.cda.suivimns.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private boolean hasRole(String role) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals(role)
                );
    }

    public boolean isAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    public boolean isTechnician() {
        return hasRole("ROLE_TECHNICIAN");
    }

    public boolean isManager() {
        return hasRole("ROLE_MANAGER");
    }

    public boolean isDirector() {
        return hasRole("ROLE_DIRECTOR");
    }
}
