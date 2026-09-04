# Seed data passwords — removing the baked-in bcrypt hash

Context: `backend/src/main/resources/data-init.sql` currently seeds every demo/dev
`app_user` row with a literal bcrypt hash (two of them, actually — one shared by
most accounts, a different one for the `guest-technician@jury.fr` /
`guest-manager@jury.fr` pair). Whenever that seed password needs to change, someone
has to hash it out-of-band and paste the result into the SQL file. This doc describes
the fix: let the application hash the password itself, through the same
`PasswordEncoder` bean it already uses for real account creation
(`AppUserService.save`/`insert`), instead of a human doing it by hand.

Related backlog item: `docs/dev/todo.md` → Backend → "Proper password management".
This doc only covers the seed-data piece of that item; the self-service reset,
brute-force protection and password-policy pieces are separate work.

## Why not just build the users in Java instead of SQL?

That was the first idea, but `data-init.sql` is one tightly-coupled script: tickets,
assignments, history, licenses, classifications and activities all reference
`app_user` rows by hardcoded numeric ID (e.g. `id_client = 1`, `id_current_technician
= 5`). Reproducing that graph from a `CommandLineRunner` means either re-implementing
all of it in Java or carefully reproducing the exact same insertion order so
`IDENTITY` hands out the same IDs — more moving parts, more ways to silently drift
from the SQL file, for no real benefit.

Instead: keep `data-init.sql` exactly as it is structurally (same rows, same order,
same IDs), just stop putting a real hash in the `password` column. Put an obvious
placeholder there instead, and let a small startup component find any row still
carrying that placeholder and replace it with a proper hash.

## Why this ordering is safe

Spring Boot runs SQL init scripts (`spring.sql.init.*`) as part of context refresh —
`AbstractScriptDatabaseInitializer` does its work from `afterPropertiesSet()`, i.e.
during bean initialization. `CommandLineRunner`/`ApplicationRunner` beans only run
*after* `context.refresh()` has fully completed (`SpringApplication.run()` calls
`callRunners(...)` after the refresh, not during it). So by the time any
`CommandLineRunner` executes, `data-init.sql` has already finished inserting every
row — the placeholder passwords are already in the table, ready to be updated.

## Step-by-step

### 1. Replace the hash literals in `data-init.sql`

Pick two placeholder strings that can never be mistaken for a real hash (so a bug in
the seeder fails closed — login just won't work — rather than silently leaving a
guessable password in place). Something like:

```sql
INSERT INTO app_user (phone_number, email, first_name, last_name, password)
VALUES ( '0684545611', 'barbara.dupont99@gmail.com', 'Barbara', 'Dupont', '{seed}'),
       ...
       ('', 'guest-technician@jury.fr', 'Test', 'Technicien', '{seed-guest}'),
       ('', 'guest-manager@jury.fr', 'Test', 'Manager', '{seed-guest}');
```

Replace every occurrence of the current `$2a$10$V98VMxgV7...` hash with `{seed}`, and
every occurrence of `$2a$10$T0VOfEeDE8...` with `{seed-guest}`.

### 2. Add the demo plaintext passwords as config, not as a code constant

In `backend/src/main/resources/application-dev.properties`:

```properties
app.seed.demo-password=changeit-demo
app.seed.demo-guest-password=changeit-guest
```

Committing a plaintext dev-only password to the repo is fine here — it's exactly as
public as the hash that was already committed (anyone with the repo can already brute
force or reuse the old hash), and now changing it is a one-line edit instead of a
re-hash. Leave these two properties **unset** in `application.properties` (the
env-var-driven default profile) — see the caveat below.

### 3. Write the seeder

New file, `backend/src/main/java/com/mns/cda/suivimns/config/DemoPasswordSeeder.java`:

```java
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
 * Turns the {seed} / {seed-guest} placeholders left in data-init.sql into real
 * bcrypt hashes, using the app's own PasswordEncoder. Runs after SQL init (see
 * docs/dev/seed-data-passwords.md) and is a no-op wherever the placeholders
 * aren't found — e.g. a schema seeded from a real environment.
 */
@Component
@RequiredArgsConstructor
public class DemoPasswordSeeder implements CommandLineRunner {

    private static final String PLACEHOLDER = "{seed}";
    private static final String GUEST_PLACEHOLDER = "{seed-guest}";

    private final AppUserDao appUserDao;
    private final PasswordEncoder encoder;

    @Value("${app.seed.demo-password:}")
    private String demoPassword;

    @Value("${app.seed.demo-guest-password:}")
    private String demoGuestPassword;

    private static final Logger logger = LoggerFactory.getLogger(DemoPasswordSeeder.class);

    @Override
    public void run(String... args) {
        rehash(PLACEHOLDER, demoPassword);
        rehash(GUEST_PLACEHOLDER, demoGuestPassword);
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
```

No `@Profile` restriction needed: the filter on the placeholder string makes it
inert against a real database (nothing will ever have `password = '{seed}'` there),
and if `app.seed.demo-password` isn't set it just logs a warning and leaves the
placeholder in place instead of guessing.

### 4. Run it

`dev` profile recreates the schema every start (`ddl-auto=create`), so just:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Then confirm `POST /log-in` works for e.g. `barbara.dupont99@gmail.com` with
whatever you put in `app.seed.demo-password`.

## Caveat: the env-var-driven default profile

`backend/.env.example` also ships `SQL_FILE=init` and `SQL_INIT_MODE=always`, so
anyone running the app the "default profile" way (`cp .env.example .env` per
`CLAUDE.md`) also loads `data-init.sql` — not just `-Dspring-boot.run.profiles=dev`.
If they don't set `app.seed.demo-password`/`app.seed.demo-guest-password` in their
`.env`/`application.properties`, the seeded accounts will keep the `{seed}` /
`{seed-guest}` placeholder and simply can't log in (seeder logs a warning saying so —
nothing silently broken, but worth calling out in the PR description). Add those two
properties to `.env.example` alongside `SQL_FILE`/`SQL_INIT_MODE` if you want that
path to work out of the box too.

## What this does *not* fix

This only removes the "paste a hash by hand" friction for seed data. The rest of the
password-management backlog (`docs/dev/todo.md`) is untouched: no self-service
"forgot password" flow, no brute-force protection on `/log-in`, and the
`NewUserDto` (min 3 chars) vs. `PasswordDto`/`NewPasswordDto` (min 8 chars) policy
mismatch still needs a decision.
