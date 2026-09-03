package com.mns.cda.suivimns.controller.business;

import com.mns.cda.suivimns.dto.account.NewUserDto;
import com.mns.cda.suivimns.dto.flat.UserLoginDto;
import com.mns.cda.suivimns.mapper.entity.DirectorMapper;
import com.mns.cda.suivimns.mapper.entity.ManagerMapper;
import com.mns.cda.suivimns.mapper.entity.TechnicianMapper;
import com.mns.cda.suivimns.model.AppUser;
import com.mns.cda.suivimns.model.Client;
import com.mns.cda.suivimns.model.Manager;
import com.mns.cda.suivimns.model.Technician;
import com.mns.cda.suivimns.model.Director;
import com.mns.cda.suivimns.security.AppUserDetails;
import com.mns.cda.suivimns.security.IsAdmin;
import com.mns.cda.suivimns.service.entity.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    protected final TechnicianMapper technicianMapper;
    protected final ManagerMapper managerMapper;
    protected final DirectorMapper directorMapper;

    protected final AuthenticationProvider authenticationProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    // Should be removed once security is set-up
    @PostMapping("/sign-in")
    @IsAdmin
    public ResponseEntity<AppUser> signIn(@RequestBody @Valid AppUser userToInsert) {

        userService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/client")
    @IsAdmin
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client userToInsert) {

        clientService.insert(userToInsert);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/manager")
    @IsAdmin
    public ResponseEntity<Manager> createManager(@RequestBody @Valid NewUserDto dto,
                                                  @AuthenticationPrincipal AppUserDetails principal) {

        Manager userToInsert = managerMapper.toNewEntity(dto);
        managerService.insert(userToInsert, principal);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/technician")
    @IsAdmin
    public ResponseEntity<Technician> createTechnician(@RequestBody @Valid NewUserDto dto,
                                                        @AuthenticationPrincipal AppUserDetails principal) {

        // Rang par defaut a la creation, modifiable ensuite via PATCH
        Technician userToInsert = technicianMapper.toNewEntity(dto);
        technicianService.insert(userToInsert, principal);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }

    @PostMapping("/director")
    @IsAdmin
    public ResponseEntity<Director> createDirector(@RequestBody @Valid NewUserDto dto,
                                                    @AuthenticationPrincipal AppUserDetails principal) {

        Director userToInsert = directorMapper.toNewEntity(dto);
        directorService.insert(userToInsert, principal);

        return new ResponseEntity<>(userToInsert, HttpStatus.CREATED);
    }


    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(
            @RequestBody UserLoginDto user) {
        logger.debug("Tentative de connexion de {}", user.email());
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

            logger.info("Connexion réussie pour {}: {}", user.email(), role);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            logger.warn("Échec de connexion : {}", user.email());
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
        }
    }
}
