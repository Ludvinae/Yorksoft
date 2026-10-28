# TODO — Staging for Trello

The backlog lives on the project's Trello dashboard (board "Yorksoft"), not in this repo. This file is just a scratch pad: draft a card here first, then move it into Trello and delete the entry — don't let items accumulate here long-term.

## [Priorité faible] Route admin pour forcer le statut d'un ticket

Un admin n'est pas un `Technician` : les actions de workflow réservées au technicien assigné (ex. `/ticket/{id}/wait`, qui charge le technicien via le principal) lui renvoient une erreur, même si le frontend lui affiche les boutons. Plutôt que d'étendre ces actions, prévoir une route dédiée (ex. `POST /ticket/{id}/force-status`, `@IsAdmin`) qui permet de forcer un statut précis, en contournant `StatusTransition`, avec justification obligatoire et trace dans l'historique / l'activité. Côté frontend, masquer alors aux admins les boutons d'action réservés au technicien et proposer cette action à la place.
