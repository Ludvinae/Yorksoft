package com.mns.cda.suivimns.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Builds the JWT returned on login (and on trial account creation).
 * Tokens still carry no expiration claim: see the "Security" section of docs/ARCHITECTURE.md.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(AppUserDetails appUser) {
        return Jwts.builder()
                .setSubject(appUser.getEmail())
                .addClaims(Map.of("role", roleOf(appUser)))
                .addClaims(Map.of("name", appUser.getName()))
                .addClaims(Map.of("id", appUser.getId()))
                .addClaims(Map.of("rank", appUser.getRank()))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public static String roleOf(AppUserDetails appUser) {
        // HERITAGE
        // The final "ADMIN" branch is not a real fallback: AppUserDetailsService only finds users through the
        // role subtype tables, so a user with no role is rejected (401) before reaching this point.
        // AppUserDetails carries a single subtype (client > technician > manager > director > admin), so
        // the order of these checks doesn't matter.
        return appUser.getManager() != null
                ? "MANAGER"
                : appUser.getTechnician() != null
                ? "TECHNICIAN"
                : appUser.getClient() != null
                ? "CLIENT"
                : appUser.getDirector() != null
                ? "DIRECTOR"
                : "ADMIN";
    }
}
