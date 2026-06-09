package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.flat.UserLoginDto;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.service.entity.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    protected final AppUserService userService;
    protected final ClientService clientService;
    protected final TechnicianService technicianService;
    protected final ManagerService managerService;
    protected  final DirectorService directorService;

    protected final AuthenticationProvider authenticationProvider;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Should be removed once security is set-up
    @PostMapping("/sign-in")
    public ResponseEntity<AppUser> signIn(@RequestBody @Valid AppUser userToInsert) {

        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/client")
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client userToInsert) {

        clientService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/manager")
    public ResponseEntity<Manager> createManager(@RequestBody @Valid  Manager userToInsert) {

        managerService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/technician")
    public ResponseEntity<Technician> createClient(@RequestBody @Valid  Technician userToInsert) {

        technicianService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/director")
    public ResponseEntity<Director> createDirector(@RequestBody @Valid  Director userToInsert) {

        directorService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }


    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(
            @RequestBody UserLoginDto user) {
        try {
            AppUserDetails appUser = (AppUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(user.email(), user.password())).getPrincipal();

            // HERITAGE
            String role = appUser.getManager() != null
                    ? "MANAGER"
                    : appUser.getTechnician() != null
                    ? "TECHNICIAN"
                    : appUser.getClient() != null
                    ? "CLIENT"
                    : appUser.getDirector() != null
                    ? "DIRECTOR"
                    : "ADMIN";

            String name = appUser.getName();
            int id =  appUser.getId();
            String rank = appUser.getRank();

            String jwt = Jwts.builder()
                    .setSubject(user.email())
                    //.addClaims(Map.of("role", appUser.getUser().getRole().getName()))
                    .addClaims(Map.of("role", role))
                    .addClaims(Map.of("name", name))
                    .addClaims(Map.of("id", id))
                    .addClaims(Map.of("rank", rank))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }
    }
}
