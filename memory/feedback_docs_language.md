---
name: docs-language-english
description: All project documentation must be written in English, regardless of the language the session is conducted in
metadata:
  type: feedback
---

All documentation in this repo (README.md, docs/ARCHITECTURE.md, docs/API.md, docs/dev/todo.md, docs/dev/journal.md, and any future doc files) must be written in English.

**Why:** User explicitly requested English-only docs on 2026-09-04, even though they often write to Claude in French. This applies regardless of what language the conversation itself is in.

**How to apply:** When creating or editing any doc file in `docs/` (or root-level `README.md`/`CLAUDE.md`), write the content in English even if the user's instructions or the conversation are in French. Code comments and commit messages follow normal project conventions, not this rule specifically, but default to English there too unless told otherwise.
