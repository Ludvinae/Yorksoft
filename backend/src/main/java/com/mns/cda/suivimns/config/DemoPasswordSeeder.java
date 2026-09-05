package com.mns.cda.suivimns.config;

import com.mns.cda.suivimns.dao.AppUserDao;
import com.mns.cda.suivimns.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Turns the {seed-role} placeholders left in data-init.sql / data-prod.sql into real
 * bcrypt hashes, using the app's own PasswordEncoder. Runs after SQL init and
 * is a no-op wherever the placeholders aren't found — e.g. a schema seeded
 * from a real environment.
 */
@Component
@RequiredArgsConstructor
public class DemoPasswordSeeder implements CommandLineRunner {

    private static final String ADMIN_PLACEHOLDER = "{seed-admin}";
    private static final String MANAGER_PLACEHOLDER = "{seed-manager}";
    private static final String TECHNICIAN_PLACEHOLDER = "{seed-technician}";
    private static final String DIRECTOR_PLACEHOLDER = "{seed-director}";
    private static final String CLIENT_PLACEHOLDER = "{seed-client}";

    private final AppUserDao appUserDao;
    private final PasswordEncoder encoder;

    @Value("${app.seed.demo-admin-password:}")
    private String demoAdminPassword;

    @Value("${app.seed.demo-manager-password:}")
    private String demoManagerPassword;

    @Value("${app.seed.demo-technician-password:}")
    private String demoTechnicianPassword;

    @Value("${app.seed.demo-director-password:}")
    private String demoDirectorPassword;

    @Value("${app.seed.demo-client-password:}")
    private String demoClientPassword;

    private static final Logger logger = LoggerFactory.getLogger(DemoPasswordSeeder.class);

    @Override
    public void run(String... args) {
        rehash(ADMIN_PLACEHOLDER, demoAdminPassword);
        rehash(TECHNICIAN_PLACEHOLDER, demoTechnicianPassword);
        rehash(MANAGER_PLACEHOLDER, demoManagerPassword);
        rehash(DIRECTOR_PLACEHOLDER, demoDirectorPassword);
        rehash(CLIENT_PLACEHOLDER, demoClientPassword);
    }

    private void rehash(String placeholder, String plaintext) {
        List<AppUser> toFix = appUserDao.findAll().stream()
                .filter(u -> placeholder.equals(u.getPassword()))
                .toList();

        if (toFix.isEmpty()) {
            return;
        }

        if (plaintext == null || plaintext.isBlank()) {
            logger.warn("{} seed user(s) still have the '{}' placeholder password " +
                            "and no app.seed.* plaintext was configured — they won't be able to log in.",
                    toFix.size(), placeholder);
            return;
        }

        String hash = encoder.encode(plaintext);
        toFix.forEach(u -> u.setPassword(hash));
        appUserDao.saveAll(toFix);
        logger.info("Seeded password for {} demo user(s) (placeholder '{}').", toFix.size(), placeholder);
    }
}