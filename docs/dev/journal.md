# Development journal

History of design/architecture decisions, session milestones, and choices made along the way — **not** a code changelog (see git history for that). Organized by date, most recent session on top. Update at the end of each work session with Claude.

## 2026-09-05

- Added `docs/RULES.md`: no code change without an explicit request (ask when in doubt), and documentation must live in the `/docs` folder matching its scope (root = general/cross-cutting, `backend/docs` / `frontend/docs` = app-specific).
- Fixed demo-account password seeding: `APP_SEED_DEMO_*_PASSWORD` env vars were missing from `backend/.env.example` and unmapped in `application.properties`, so `DemoPasswordSeeder` had nothing to hash on the env-var-driven (prod-like) profile — accounts stayed stuck on the `{seed-role}` placeholder.
- Added `data-prod.sql` (`SQL_FILE=prod`) to seed only the admin account for a real deploy, instead of the full demo dataset in `data-init.sql`. Known gap surfaced but intentionally left as-is: it doesn't seed reference/catalog data (e.g. `activity_type`) — the admin is expected to load that via a script to be added later, since bootstrapping a ticket isn't the admin's role anyway.
- Restructured `/docs`: moved backend-only content (`API.md`, DB conception diagrams, ticket-lifecycle/use-case images) into `backend/docs/`, frontend-only content into `frontend/docs/`, split `ARCHITECTURE.md` and `docs/dev/todo.md` by scope accordingly. Root `docs/` now holds only project-wide material (overview, security, known issues, cross-cutting todo). Updated all cross-references (`README.md`, `CLAUDE.md`, both app `README.md`s, the `update-docs` skill).

## 2026-09-04

- Set up `docs/dev/todo.md`: tracks remaining features to integrate, organized into Frontend / Backend / Other, to be kept up to date as development progresses.
- Decision: JWT invalidation should happen server-side (blacklist/revocation) rather than by just deleting the token from `localStorage` on logout — added to the todo as a security workstream, tied to the planned move to short-lived tokens + refresh.
- Decision: all project documentation (including this journal and the todo list) must be written in English.
