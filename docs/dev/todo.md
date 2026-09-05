# TODO — Remaining features to integrate

Tracking list for unfinished features and workstreams, to be kept up to date as development progresses (check `[x]` once done, or remove the entry and, if relevant, carry it over into `README.md#project-status` / `docs/ARCHITECTURE.md`).

This file holds cross-cutting items only. Per-app backlogs live next to each app: [backend/docs/todo.md](../../backend/docs/todo.md), [frontend/docs/todo.md](../../frontend/docs/todo.md).

Sources as of now: `README.md` ("Project status" section), `docs/ARCHITECTURE.md` ("Known gaps" / "Known issues") and `CLAUDE.md`.

## Other

- [ ] GDPR compliance: define a data retention period, implement a right-to-erasure/anonymization flow
- [ ] Accessibility audit (RGAA/WCAG) — never done
- [ ] Automated, tested backup/restore procedure for Postgres (the Docker volume survives container recreation but that is not a backup)
- [ ] Load/volume testing (none today)
- [ ] Measured code coverage (not instrumented today)
- [ ] Root-level `docker-compose.yml` unifying `backend/` and `frontend/` (each app has its own, no global composition)
