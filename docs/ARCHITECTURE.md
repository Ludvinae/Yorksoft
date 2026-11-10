# Architecture

General, cross-cutting architecture notes. Per-app implementation details live next to each app: [backend/docs/ARCHITECTURE.md](../backend/docs/ARCHITECTURE.md) (+ [backend/docs/API.md](../backend/docs/API.md)) and [frontend/docs/ARCHITECTURE.md](../frontend/docs/ARCHITECTURE.md).

## Overview

Suivi MNS is a ticketing (support/helpdesk) application split into two independent applications that communicate over a REST API:

![Frontend Angular, backend Spring Boot and PostgreSQL, connected through a REST/JSON+JWT API and JPA/SQL](assets/architecture-layers.png)

The repo is a monorepo: `backend/` and `frontend/` are two independently deployable projects (each has its own `Dockerfile` and `compose.yml`), kept together here to share a common history and documentation.

## Security

- Login (`POST /log-in`) checks a BCrypt-hashed password and returns a stateless JWT, signed with HS256 (secret injected via `JWT_SECRET`), carrying the user's identity and role. No route ever returns a password or its hash.
- The token is stored in the frontend's `localStorage` and attached to requests by an HTTP interceptor; "logout" only deletes the local copy — there's no server-side revocation, consistent with the token having no expiration (see below).
- Authorization is enforced on the backend by role via `@PreAuthorize` (the `Is*` annotations), plus ownership checks in the services themselves where role alone isn't enough (e.g. a client may only read their own tickets, a technician may only act on tickets assigned to them). The frontend only hides menus/actions for convenience and is not treated as a security boundary.
- Public demo (portfolio deploys only, `APP_DEMO_ENABLED`): anyone can get a trial technician account from the login page, without personal data. Abuse is bounded by a per-IP creation limit, a cap on active trial accounts, per-account hourly limits on tickets and comments (anti-flooding), restricted rights (rank 0) and an automatic purge after 24 h; the purge also makes those accounts' non-expiring JWTs useless, since the JWT filter reloads the user on every request. Never enable it on a real deployment.
- DTOs validate input (`@NotNull`, `@NotBlank`, `@Size`, `@Email`, `@Min`/`@Max`, `@Pattern` — e.g. passwords require 8–16 characters with at least one digit, one uppercase and one lowercase letter); JPA queries are parameterized (no string-concatenated SQL) and sortable fields are whitelisted (see [backend/docs/ARCHITECTURE.md](../backend/docs/ARCHITECTURE.md#search-filtering-and-pagination)); a global exception handler turns business/validation errors into a consistent response shape without leaking internal details.
- **Known gaps, planned as upcoming work**:
  - JWTs currently carry no expiration claim and never expire; short-lived tokens with a refresh/auto-renewal flow are planned. The `localStorage` vs. `HttpOnly` cookie tradeoff (and the CSRF strategy that comes with a cookie) hasn't been decided yet.
  - CORS only allows the origins listed in `app.cors.allowed-origins` (`APP_CORS_ALLOWED_ORIGINS`): the production domain by default, `http://localhost:4200` in the `dev` profile. In production the front calls the API through `/api` on the same domain, so CORS doesn't come into play there.
  - No systematic review against the OWASP Top 10 has been done yet; this is planned as a dedicated hardening pass.
  - No GDPR / accessibility (RGAA/WCAG) compliance work has started yet: no defined data retention period, no right-to-erasure/anonymization flow, no formal accessibility audit.
  - No automated, tested backup/restore procedure exists. The Postgres Docker volume survives container recreation, but that is explicitly not a backup.
  - The schema is Hibernate-generated (`ddl-auto`) rather than versioned via a migration tool (Flyway/Liquibase).

## Testing

Per-app testing approach: [backend/docs/ARCHITECTURE.md#testing](../backend/docs/ARCHITECTURE.md#testing) and [frontend/docs/ARCHITECTURE.md#testing](../frontend/docs/ARCHITECTURE.md#testing).

- No load/volume testing and no measured code coverage yet, on either app.

## Known issues

- No open known issue. The SLA/timestamp display that was off by 1–2 hours on the deployed server was fixed on 2026-11-10: the backend's zone-less `LocalDateTime` values were written in the JVM's zone (UTC on the server, `Europe/Paris` on a dev machine) and sent without an offset, so browsers read them as local time. See "Dates and time zones" in [backend/docs/ARCHITECTURE.md](../backend/docs/ARCHITECTURE.md).
