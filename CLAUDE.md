# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository structure

Monorepo containing two independently deployable applications, merged (with full commit history preserved via `git subtree`) from two originally separate repos:

- `backend/` — Spring Boot REST API (Java 17)
- `frontend/` — Angular SPA

Each has its own `Dockerfile`, `compose.yml`, and CI workflow (`backend/.github/workflows`, `frontend/.github/workflows`); there is no unified root `docker-compose.yml` yet. See `README.md` for the feature/stack overview. Documentation is split by scope: `docs/` at the repo root holds only general, cross-cutting material (project-wide overview, security model, known issues); per-app details live in `backend/docs/` (`ARCHITECTURE.md`, `API.md`) and `frontend/docs/` (`ARCHITECTURE.md`) — see `docs/RULES.md`. Keep these in sync when this file's description of the architecture changes.

## Commands

### Backend (`backend/`)

- Run locally, zero config (dev profile, expects local Postgres, recreates schema on start): `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
- Run with the default (env-var driven) profile: copy `backend/.env.example` to `backend/.env`, then `./mvnw spring-boot:run`
- Run all tests: `./mvnw test`
- Run a single test class: `./mvnw test -Dtest=AssignmentControllerUnitTest`
- Run a single test method: `./mvnw test -Dtest=AssignmentControllerUnitTest#methodName`
- Build the jar: `./mvnw clean package -DskipTests`
- Tests live under `src/test/java/.../unit/` and `.../integration/` — mind which kind you're adding/running.
- Local Postgres for dev: `docker run --name suivi-mns-db -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=db -p 5432:5432 -d postgres:18`

### Frontend (`frontend/`)

- Install deps: `npm install`
- Dev server: `npm start` (http://localhost:4200, calls the API at http://localhost:8080 — see `src/environments/environment.development.ts`)
- Build: `npm run build`
- Run all tests (Vitest): `npm test`
- Run a single test file: `npx vitest run src/app/path/to/file.spec.ts`
- No lint script is configured. `.prettierrc` exists but isn't wired to an npm script.

## Architecture

- **Ticket lifecycle is the core domain concept, and it's not a simple status field.** Each transition (open → assign → progress → wait → solve → close/reject) is handled by its own service under `backend/src/main/java/com/mns/cda/suivimns/service/workflow/` (`TicketOpeningService`, `TicketAssignmentService`, `TicketProgressService`, `TicketWaitService`, `TicketSolvedService`, `TicketClosingService`, `StatusTransition`). Priority (`initialPriority`/`currentPriority`) and SLA (`slaDeadline`/`overdue`) are derived as part of these transitions. When changing ticket status logic, find the relevant workflow service rather than editing `Ticket`/`TicketController` directly.
- **User/role model uses JPA `JOINED` inheritance, not a role column.** `AppUser` is the base entity; `Admin`, `Director`, `Manager`, `Technician`, `Client` are subtypes with their own joined tables. A user's role is derived from *which subtype exists* for them — see `AuthController.logIn`, which checks `appUser.getManager() != null ? ... : ...` to build the JWT `role` claim. When adding role-scoped behavior, follow this subtype-check pattern rather than introducing a role enum.
- **Authorization uses custom annotations that wrap `@PreAuthorize`**, not inline `@PreAuthorize` strings: `@IsAdmin`, `@IsDirector`, `@IsManager`, `@IsTechnician`, `@IsClient`, `@IsEmployee`, `@IsUser` (`backend/src/main/java/com/mns/cda/suivimns/security`). Use one of these on new controller methods.
- **Controllers talk in DTOs, never entities** (`TicketController` is the clearest example: `TicketDto`, `TicketListDto`, `TicketDetailFullDto`, `TicketCreationDto`, `TicketDescriptionDto`), mapped via MapStruct mappers in `mapper/entity`. A few older endpoints in `AuthController` (`/sign-in`, `/client`) still accept entities directly — don't copy that pattern for new code.
- **JWTs are stateless (HS256) and currently carry no expiration claim** — `AuthController.logIn` and `JwtFilter` neither set nor check `exp`. This is a known, tracked gap (see `README.md` "Project status" and `docs/ARCHITECTURE.md` "Security"), not an oversight to silently fix — align any auth work with the planned short-lived-token/refresh design mentioned there.
- **Frontend mirrors the backend resource-by-resource**: one service per REST resource under `frontend/src/app/services/api/`, with `services/business/` layering app-level logic on top (e.g. a ticket store). `models/` mirrors backend DTO shapes — a backend DTO change usually needs a matching update on the frontend model/service.
- **The UI branches by role and by viewport, not via one responsive component tree**: dashboards differ per role (`components/dashboard/{admin,director,manager,technician}-dashboard`), and ticket lists have distinct desktop/mobile components (`components/list/desktop-table` vs `mobile-card-list`, `components/ticket/list-desktop`) — check both sides when changing ticket-list behavior.
- **Config**: outside the `dev` Spring profile, the backend is fully env-var driven (`DB_HOST/PORT/NAME/USER/PASSWORD`, `JWT_SECRET`, `DDL_AUTO`, `SQL_FILE`, `SQL_INIT_MODE` — see `backend/.env.example`). The `dev` profile (`application-dev.properties`) hardcodes local values and `ddl-auto=create` for zero-config local development. `SQL_FILE` picks which `data-${SQL_FILE}.sql` seed runs: `init` (default) loads the full demo dataset for dev/CI, `prod` (`data-prod.sql`) seeds only the admin account for a real deploy. Neither file stores a real password — accounts are seeded with `{seed-role}` placeholders that `DemoPasswordSeeder` rehashes via BCrypt at startup, one plaintext per role from `app.seed.demo-*-password` (`APP_SEED_DEMO_*_PASSWORD` env vars); it's a no-op once the placeholder is gone, so never hardcode a real password into a `data-*.sql` file.
- **`ActivityType` is both an enum and a JPA entity — they serve different purposes and both must be kept in sync.** `enumerate.ActivityType` (`TICKET`, `COMMENT`, `PROCEDURE`, `ASSIGNMENT`, `USER`, `OTHER`) is the compile-time-safe value passed to `ActivityService.log(user, description, ActivityType.X)`; `model.ActivityType` is the DB-backed catalog row (`designation`, display `name`, `iconAddress`) it's resolved against via `ActivityTypeDao.findByDesignation(...)`. If a database has no matching catalog row, `ActivityService.log` throws — a schema/seed missing `activity_type` rows breaks ticket opening, comments, assignments, etc. wherever they call it.
- **Ticket status changes go through explicit action routes** (`/ticket/{id}/assign`, `/start-progress`, `/wait`, `/resume-progress`, `/solve`, `/close`), not a generic status `PATCH` — each maps to one `service/workflow` service and one legal-transition check in `StatusTransition`. A business-rule violation (e.g. resolving with no solution recorded) should surface as `409 Conflict`, not `400`/`500`.
- **Known bug, not yet root-caused**: SLA/timestamp display has been observed off by ~2 hours on a deployed (non-local) environment, not reproducible locally — suspected UTC vs. `Europe/Paris` mismatch. If you touch SLA or date/time logic, keep timestamps in UTC end-to-end and only convert at the display layer; see `docs/ARCHITECTURE.md` "Known issues".
