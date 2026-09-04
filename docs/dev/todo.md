# TODO — Remaining features to integrate

Tracking list for unfinished features and workstreams, to be kept up to date as development progresses (check `[x]` once done, or remove the entry and, if relevant, carry it over into `README.md#project-status` / `docs/ARCHITECTURE.md`).

Sources as of now: `README.md` ("Project status" section), `docs/ARCHITECTURE.md` ("Known gaps" / "Known issues") and `CLAUDE.md`.

## Frontend

- [ ] Role-based dashboards: finish the missing KPIs/charts for Director and Admin (functional for Manager, still very incomplete elsewhere)
- [ ] Mobile ticket list: the mobile layout still renders the desktop table instead of a card-based view (`mobile-card-list`)
- [ ] Knowledge base UI (the backend already exists, the dedicated UI is still to be built)
- [ ] Client-facing portal (clients currently go through a technician, no direct app access)
- [ ] Notifications / alerts UI (approaching/breached SLA, new assignments...)
- [ ] Internal user management beyond account creation (edit, deactivate) — UI side
- [ ] Internationalization (the app is French-only today)
- [ ] Strengthen frontend tests (Vitest): go beyond "does the component construct" to test interaction behavior (clicks, form validation, filters)
- [ ] Browser-driven end-to-end tests (none today)

## Backend

- [ ] Internal user management beyond account creation (edit, deactivate) — API side
- [ ] Notifications / alerts backend (approaching/breached SLA detection, new-assignment events...)
- [ ] Short-lived JWTs + automatic refresh flow (tokens currently never expire); decide `localStorage` vs `HttpOnly` cookie (and the CSRF strategy that comes with it)
- [ ] Server-side JWT invalidation (blacklist/revocation) instead of just deleting the token from `localStorage` on logout — a stolen token currently stays valid until this changes
- [ ] Restrict CORS to the frontend's origin (currently `allowedOrigins: *`)
- [ ] OWASP Top 10 security review (never formally done)
- [ ] Migrate the schema to a versioned migration tool (Flyway/Liquibase) instead of `ddl-auto`
- [ ] Known bug: SLA/timestamp display off by ~2 hours on a deployed environment, not reproducible locally — suspected UTC vs. `Europe/Paris` mismatch; keep timestamps in UTC end-to-end and only convert at the display layer

## Other

- [ ] GDPR compliance: define a data retention period, implement a right-to-erasure/anonymization flow
- [ ] Accessibility audit (RGAA/WCAG) — never done
- [ ] Automated, tested backup/restore procedure for Postgres (the Docker volume survives container recreation but that is not a backup)
- [ ] Load/volume testing (none today)
- [ ] Measured code coverage (not instrumented today)
- [ ] Root-level `docker-compose.yml` unifying `backend/` and `frontend/` (each app has its own, no global composition)
