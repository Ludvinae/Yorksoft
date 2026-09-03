# Suivi MNS

Web-based ticketing (support/helpdesk) application built as part of a **CDA (Concepteur Développeur d'Applications)** software developer certification capstone project.

A client reports an incident on a software product they use; the support team (technician → manager → director) qualifies it, assigns it, works it and closes it, with SLA tracking and automatic prioritization.

> Stack: **Angular 21** (Tailwind CSS + DaisyUI) · **Spring Boot 3** (Java 17, Hibernate) · **PostgreSQL** (Docker)

## Table of contents

- [Features](#features)
- [Tech stack](#tech-stack)
- [Getting started](#getting-started)
- [Documentation](#documentation)
- [Project status](#project-status)

## Features

- **Full ticket lifecycle**: opening, qualification (theme/urgency/impact), automatic priority and SLA calculation, assignment, handling, on-hold states (waiting on client / third party), resolution, closing.
- **Role-based dashboards**: KPIs and charts for administrators, directors, managers and technicians.
- **Business reference data management** (full CRUD, admin-only): software and software types, versions and version types, themes, urgency levels, impact levels, statuses.
- **Client records** and management of the software licenses tied to them.
- **Comment thread and activity history** on every ticket, for traceability of exchanges and actions.
- **Knowledge base**: linking resolution articles/procedures to tickets.
- **Role-based authentication** (JWT) with five profiles: Admin, Director, Manager, Technician, Client.
- Responsive UI with dedicated desktop/mobile views for ticket lists.

## Tech stack

| | |
|---|---|
| **Frontend** | Angular 21 (standalone components, signals) · Tailwind CSS 4 · DaisyUI 5 · Chart.js |
| **Backend** | Spring Boot 3.2 (Java 17) · Spring Security (JWT) · Spring Data JPA / Hibernate · MapStruct |
| **Database** | PostgreSQL 18, containerized (Docker) |
| **API documentation** | Auto-generated OpenAPI / Swagger UI (springdoc) |
| **CI/CD** | GitHub Actions (build, tests, Docker image, deployment) |

Design rationale and details: see [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Getting started

Prerequisites: Java 17, Node.js 20+, Docker.

### 1. Database

```bash
docker run --name suivi-mns-db -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=db -p 5432:5432 -d postgres:18
```

### 2. Backend

```bash
cd backend
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The `dev` profile (`application-dev.properties`) points directly at the local database above and recreates the schema on every startup — no extra configuration needed for local development.

To run with the default profile instead (the one used in production, fully driven by environment variables), copy `backend/.env.example` to `backend/.env` and adjust it.

The API starts on `http://localhost:8080` (Swagger docs at `/swagger-ui.html`).

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

The app is served on `http://localhost:4200` and calls the API on `http://localhost:8080` (see `frontend/src/environments`).

## Documentation

- [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) — architecture, tech choices, data model (MCD/MLD), roles and security
- [docs/API.md](docs/API.md) — authentication, roles, REST API resources

## Project status

The core business logic (ticket lifecycle, reference data, dashboards) works end to end. Still in progress:

- Role-based dashboards: many KPIs and charts are still to be built
- Internal user management beyond account creation (edit, deactivate)
- Knowledge base UI (the backend exists, the dedicated UI is still to be built)
- Notifications and alerts (approaching/breached SLA, new assignments...)
- General UI polish
- **GDPR and accessibility (RGAA/WCAG) compliance** — not addressed yet, planned as a dedicated workstream
- **Security hardening**: short-lived JWTs with refresh/auto-renewal (tokens currently don't expire), and a systematic review against the OWASP Top 10

## Author

Project built by Benoit Champlon as part of the CDA certification.
