---
name: journal
description: Add a dated entry to docs/dev/journal.md summarizing this session's decisions, architecture choices, and milestones — not a code changelog. Use when the user asks to update/log the journal, or types /journal.
---

Update `docs/dev/journal.md` with an entry for today's session, following the conventions already stated at the top of that file.

## Process

1. Read `docs/dev/journal.md` — check its most recent entry and confirm today's date (`## YYYY-MM-DD`) doesn't already have a heading.
2. Review this conversation (and `git log`/`git diff` if it helps) for what actually happened this session: decisions made and why, tradeoffs, anything a future session would need that isn't obvious from re-reading the code or commit messages alone.
3. Write concise bullets, one per decision/milestone, matching the file's existing terse style. English only (per `docs/RULES.md`).
4. If today already has a heading, append bullets to it instead of duplicating the heading. Otherwise add a new `## YYYY-MM-DD` heading at the top (most recent first).
5. Report back a one-line summary of what was added.

## Non-goals

- Don't log routine or uneventful work — if nothing in this session rises to the level of a decision or milestone, say so instead of forcing an entry.
- Don't duplicate `docs/dev/todo.md` (backlog items) or restate what's already inferable from `git log` — this file is for context git history doesn't carry.
- Don't invent details not actually established in this session.
