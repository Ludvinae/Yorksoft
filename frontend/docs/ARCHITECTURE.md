# Frontend architecture

Frontend-specific details. See [../../docs/ARCHITECTURE.md](../../docs/ARCHITECTURE.md) for the project-wide overview, security model and known issues, and [../../backend/docs/API.md](../../backend/docs/API.md) for the REST API this app consumes.

## Stack

| Component | Choice | Role |
|---|---|---|
| Framework | Angular 21 (standalone, signals) | SPA |
| Styling | Tailwind CSS 4 + DaisyUI 5 | Utility-first design system |
| Charts | Chart.js / ng2-charts | Dashboards |
| Icons | Phosphor Icons | — |
| Tests | Vitest | Unit tests |

## Layout (`frontend/src/app`)

| Folder | Content |
|---|---|
| `pages` | One page per route (tickets, clients, reference data admin, knowledge base, settings...) |
| `components` | Reusable components (role dashboards, tables, generic forms, modals) |
| `services/api` | One HTTP client per REST resource (mirrors the backend controllers) |
| `services/business` | Application logic layered on top of the API services (e.g. ticket store) |
| `services/form-service`, `services/ui` | Form / UI helpers |
| `guards`, `interceptors` | Route protection and JWT injection into requests |
| `models` | TypeScript types mirroring backend DTOs |
| `validators`, `pipe` | Form validation and display transforms |

The UI adapts its content to the logged-in user's role (separate dashboards and menus for Admin, Director, Manager, Technician), with dedicated desktop and mobile views for ticket lists.

Role dashboards are functional but still a work in progress — several KPIs and charts planned for each role are not implemented yet (see [project status](../../README.md#project-status)).

## Testing

Vitest unit tests (`npm test`), currently weighted toward "does this component/service construct" rather than interaction behavior (clicks, form validation, filters) — called out in [Project status](../../README.md#project-status) as an area to deepen, along with adding some browser-driven end-to-end coverage.
