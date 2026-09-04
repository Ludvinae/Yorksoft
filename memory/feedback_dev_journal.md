---
name: feedback-dev-journal
description: Maintain docs/dev/journal.md automatically at the end of each work session
metadata:
  type: feedback
---

Automatically add an entry to `docs/dev/journal.md` at the end of each session (when wrapping up work, before/at the final response), without waiting to be asked.

**Why:** The user wants a running record of design/architecture decisions, session milestones, and choices made — explicitly *not* a redundant log of code changes (git history already covers that). They asked for this to happen automatically going forward, not on-demand.

**How to apply:**
- Entry goes under today's date, most-recent-first (new date section added at the top of the file — see file for exact heading format).
- Only capture what git commits/diffs can't show: architecture/design decisions, tradeoffs discussed, roadmap changes (e.g. items added to [[dev-todo-list]]), milestones reached — not "changed file X to do Y".
- Skip the entry (or keep it minimal) if the session had no real decisions/milestones — don't pad it with routine edits.
- If an entry for today's date already exists (multiple sessions same day), append to that day's section rather than creating a duplicate date heading.
- Related: [[dev-todo-list]] (docs/dev/todo.md, the feature backlog — also user-facing, kept up to date as work progresses).
- Write the entry in English (see [[docs-language-english]]) even if the session was conducted in French.
