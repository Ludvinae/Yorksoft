# TODO — Staging for Trello

The backlog lives on the project's Trello dashboard (board "Yorksoft"), not in this repo. This file is just a scratch pad: draft a card here first, then move it into Trello and delete the entry — don't let items accumulate here long-term.

## Bug: KPI card borders have uneven thickness (Frontend)

On the dashboards, some borders between KPI cards are thicker than others.
Lead (unverified): the KPI grids combine `border` with `sm:divide-x divide-y` on a multi-column `grid`. `divide-*` puts a border on every child but the first, regardless of grid rows, so some borders double up (e.g. the container border plus a first-row card's border). A grid with `gap-px` over a background in the border color would give even separators.
