# Development journal

History of design/architecture decisions, session milestones, and choices made along the way — **not** a code changelog (see git history for that). Organized by date, most recent session on top. Update at the end of each work session with Claude.

## 2026-09-04

- Set up `docs/dev/todo.md`: tracks remaining features to integrate, organized into Frontend / Backend / Other, to be kept up to date as development progresses.
- Decision: JWT invalidation should happen server-side (blacklist/revocation) rather than by just deleting the token from `localStorage` on logout — added to the todo as a security workstream, tied to the planned move to short-lived tokens + refresh.
- Decision: all project documentation (including this journal and the todo list) must be written in English.
