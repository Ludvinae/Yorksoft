# TODO — Frontend

Frontend-specific backlog. Cross-cutting items (GDPR, accessibility, backups, load testing...) live in [../../docs/dev/todo.md](../../docs/dev/todo.md); backend-specific items in [../../backend/docs/todo.md](../../backend/docs/todo.md).

- [ ] Role-based dashboards: finish the missing KPIs/charts for Director and Admin (functional for Manager, still very incomplete elsewhere)
- [ ] Mobile ticket list: the mobile layout still renders the desktop table instead of a card-based view (`mobile-card-list`)
- [ ] Knowledge base UI (the backend already exists, the dedicated UI is still to be built)
- [ ] Client-facing portal (clients currently go through a technician, no direct app access)
- [ ] Notifications / alerts UI (approaching/breached SLA, new assignments...)
- [ ] Internal user management beyond account creation (edit, deactivate) — UI side
- [ ] Internationalization (the app is French-only today)
- [ ] Strengthen frontend tests (Vitest): go beyond "does the component construct" to test interaction behavior (clicks, form validation, filters)
- [ ] Browser-driven end-to-end tests (none today)
