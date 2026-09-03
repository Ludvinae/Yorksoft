---
name: update-docs
description: Review the current state of backend/ and frontend/ against the project docs (README.md, docs/ARCHITECTURE.md, docs/API.md, CLAUDE.md) and update whatever has drifted out of sync. Use after implementing a feature, changing the API surface, adding/removing a dependency, or whenever the docs are suspected stale.
---

Keep this project's documentation accurate by re-deriving it from the code, not by trusting what the docs currently say.

## Scope

Four files, each with a distinct audience — update only what actually changed:

- `README.md` — features list, tech stack table, getting-started steps, "Project status" section
- `docs/ARCHITECTURE.md` — package/folder layout tables, data model, ticket lifecycle diagram, roles/security section
- `docs/API.md` — auth flow, roles table, resource route table
- `CLAUDE.md` — commands (build/test/run), and the architecture bullets (cross-file patterns a fresh session wouldn't otherwise notice)

## Process

1. **Diff reality against the docs**, don't just skim for typos:
   - Backend: `find backend/src/main/java -iname "*Controller.java"` for the resource table in `docs/API.md`; check `pom.xml` for dependency/version changes; check `application.properties`/`application-dev.properties`/`.env.example` for config drift; check `service/workflow/` for ticket-lifecycle changes; check the `security` package for role/annotation changes.
   - Frontend: `package.json` for stack version changes; `src/app/pages` and `src/app/components` for new/removed features; `src/environments` for API base URL changes.
   - Grep for anything a doc currently claims as a "known gap" (e.g. JWT expiration, CORS, OWASP, GDPR/RGAA) and check whether the code has actually changed — don't remove a caveat without verifying in the code that it no longer applies.
2. **Cross-check specific factual claims before writing them** — a claim naming a file, endpoint, annotation, or config key is a claim that it exists *now*. Grep/read to confirm rather than propagating what the doc used to say.
3. **Write the updates**, matching each file's existing tone/language (English) and structure (don't restructure headings unless the content genuinely no longer fits).
4. **Report a short diff summary** to the user: which files changed and why, and flag anything you weren't able to verify (e.g. a claim about production behavior you can't confirm from the repo alone).

## Non-goals

- Don't touch `backend/README.md` / `frontend/README.md` — they're intentionally thin pointers to the root docs.
- Don't invent roadmap items, screenshots, or metrics that aren't grounded in the code or in what the user told you directly.
- Don't run this as a blind rewrite — if nothing has drifted, say so and leave the files alone.
