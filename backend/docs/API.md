# API

## Interactive documentation

The backend embeds [springdoc-openapi](https://springdoc.org/). Once the application is running, the full, testable documentation of every endpoint (parameters, request/response schemas, status codes) is available at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw OpenAPI spec: `http://localhost:8080/api-docs`

The API surfaces over 100 routes across the resources below (all backed by generated Swagger docs). This document doesn't duplicate that auto-generated reference: it gives the overview (authentication, roles, resources) needed to use it effectively.

## Authentication

The API is stateless (no server-side session) and uses an HS256-signed JWT.

```
POST /log-in
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "..."
}
```

`200 OK` response: the JWT as plain text, to be sent on every protected request via the header:

```
Authorization: Bearer <jwt>
```

The token embeds the user's role (`role`), id (`id`), name (`name`) and rank (`rank`), derived from the account subtype (`Admin` / `Director` / `Manager` / `Technician` / `Client`).

> The current implementation issues tokens with no expiration claim — see [../../docs/ARCHITECTURE.md](../../docs/ARCHITECTURE.md#security) for the planned fix (short-lived JWTs + refresh).

A `401 Unauthorized` is returned on failed authentication; a `403 Forbidden` if the JWT's role lacks the rights required on the called route.

### Trial accounts (public demo)

Public routes, meant for a portfolio deploy and disabled unless `APP_DEMO_ENABLED=true`:

- `GET /demo` → `{ "enabled": bool, "ttlHours": n }`, used by the login page to show the "try it" button.
- `POST /demo/technician` → `201` with a JWT as plain text (same format as `/log-in`) for a new trial technician: rank 0, generated name, `@demo.invalid` email, no usable password. `404` when the demo is disabled, `429` (with `Retry-After`) after 10 accounts per IP per hour, `503` when 200 trial accounts are already active.
- Anti-flooding: a trial account can create 20 tickets and 20 comments per hour (`APP_DEMO_MAX_TICKETS_PER_HOUR`, `APP_DEMO_MAX_COMMENTS_PER_HOUR`); beyond that `POST /ticket` and `POST /comment` return `429 TRIAL_RATE_LIMIT` with `Retry-After`. Real accounts are never limited.

A trial account (rank `N0` in the JWT) can do everything a technician can on the tickets it creates, and comments them like any rank 1 technician (see Comments below), but gets `403 TRIAL_ACCOUNT_RESTRICTED` when editing a knowledge article it didn't write. It is purged, with everything it created, after `APP_DEMO_TTL_HOURS` (24 by default); its JWT stops working then.

## Roles

| Role | Description |
|---|---|
| `ADMIN` | Full administration (reference data, internal account creation) |
| `DIRECTOR` | Global oversight/reporting |
| `MANAGER` | Manages a team of technicians, assigns tickets |
| `TECHNICIAN` | Works assigned tickets |
| `CLIENT` | Opens and tracks their own tickets |

Permissions are enforced on the backend (`@IsAdmin`, `@IsManager`, `@IsTechnician`, `@IsDirector`, `@IsClient`, `@IsEmployee`, `@IsUser` annotations — see [../../docs/ARCHITECTURE.md](../../docs/ARCHITECTURE.md#security)), not only hidden in the frontend.

## Main resources

All routes are relative to the backend root (`http://localhost:8080` locally). Standard REST CRUD (`GET` list/detail, `POST`, `PUT`/`PATCH`, `DELETE`) applies to each of these resources; the exact route details are in Swagger UI.

| Resource | Base route | Description |
|---|---|---|
| Authentication | `/log-in`, `/demo` | Login, JWT issuance; trial accounts for the public demo (see [Trial accounts](#trial-accounts-public-demo)) |
| Tickets | `/ticket` | Full ticket lifecycle — plus explicit action routes: `/ticket/{id}/assign`, `/start-progress`, `/wait`, `/resume-progress`, `/solve`, `/close` (each enforces the legal-transition rules from [ARCHITECTURE.md](ARCHITECTURE.md#ticket-lifecycle) instead of a generic status `PATCH`); `PATCH /ticket/{id}/knowledge` (`{ "idKnowledge": n }`) links a knowledge article to the ticket (assigned technician, manager or admin; `409` once the ticket is closed) |
| Assignments | `/assignment` | Assigning tickets to technicians |
| Comments | `/comment` | Discussion thread on a ticket — `POST` is open to employees, by rank for technicians: rank 0 (trial) and 1 only on tickets they created or are assigned to, rank 2+ on any ticket (`403 UNAUTHORIZED_TECHNICIAN` otherwise). `GET /ticket/{id}/detail` returns a `canComment` flag so the UI shows the form only when allowed |
| History | `/history` | Activity log (audit trail) |
| Classification | `/classification` | Ticket's theme/urgency/impact tagging |
| Knowledge base | `/knowledge` | Knowledge articles linked to tickets — `GET /knowledge/articles?search=&idTheme=` returns full articles (theme, author name and `idAuthor`, versions, procedures) for the knowledge base page, readable by every employee role; the author is set from the JWT on `POST /knowledge` |
| Procedures | `/procedure` | Resolution procedures |
| Clients | `/client` | Client accounts |
| Internal users | `/user`, `/manager`, `/technician`, `/director` | Internal accounts by role |
| Software | `/software`, `/software-type` | Reference data for tracked software |
| Versions | `/version`, `/version-type` | Reference data for software versions |
| Licenses | `/license` | Licenses tied to software/clients |
| Themes | `/theme` | Ticket theme reference data |
| Urgency / Impact | `/urgency`, `/impact` | Reference data used in priority calculation |
| Statuses | `/status` | Status reference data |
| Dashboards | `/dashboard/{timeframeInDays}` | Aggregated KPIs and recent-activity feed per role. For technicians: current workload on their assigned tickets, statistics over the period (time in progress, assignment → in progress, resolution ratio, closed per day/week; durations in seconds), active tickets by status and by age since opening, an evolution series (taken in charge / closed, daily up to 31 days, weekly beyond) and SLA respect of closed tickets (on time / late, weekly up to 90 days, monthly beyond) |

## Error format

Business and validation errors are caught by the backend's `exception` layer and returned with a consistent HTTP status, body shape, and no internal detail leakage:

| Status | Meaning |
|---|---|
| `400 Bad Request` | Invalid input (failed DTO validation, illegal status transition) |
| `401 Unauthorized` | Missing or invalid authentication |
| `403 Forbidden` | Authenticated, but the role/ownership check fails |
| `404 Not Found` | Referenced resource doesn't exist |
| `409 Conflict` | Valid request, but it conflicts with the resource's current state (e.g. resolving a ticket with no solution recorded yet) |
| `500 Internal Server Error` | Unexpected error |
