# TODO — Staging for Trello

The backlog lives on the project's Trello dashboard (board "Yorksoft"), not in this repo. This file is just a scratch pad: draft a card here first, then move it into Trello and delete the entry — don't let items accumulate here long-term.

## Procédures sur la page Base de connaissances

- Pouvoir créer, modifier et supprimer les procédures d'un article directement depuis la page Base de connaissances (aujourd'hui elles sont seulement affichées en lecture).
- Le backend a déjà les routes `/procedure` (création/modification par technicien, modification limitée à l'auteur ou un admin, suppression admin) : il reste surtout le frontend (formulaire, boutons, rafraîchissement de l'article) et à vérifier les droits côté UI.
- Bonus possible : créer un nouvel article depuis cette page (aujourd'hui uniquement depuis la fiche ticket).

## Cartes KPI redirigeant vers la liste des clients

- Aujourd'hui `kpi-card` ne sait rediriger que vers la liste des tickets (input `filters` de type `TicketSearchCriteria`, appliqué via `TicketStoreService`).
- L'adapter pour qu'une carte puisse aussi ouvrir la liste des clients avec des filtres pré-appliqués (critères de recherche client + store/service correspondant).
- Cas d'usage : rendre cliquables les KPI de la rangée « Utilisateurs » du dashboard admin.

## Page d'édition des clients

- Ajouter une page d'édition des clients, accessible via un bouton depuis la liste des clients (`pages/client/client-list`).
- Le composant `pages/client/client-edit` existe déjà mais n'est qu'un squelette généré (« client-edit works! »), sans route : il reste à le brancher et à construire le formulaire.
- Côté backend, `PATCH /client/{id}` existe déjà : mais `@IsClient` n'autorise que CLIENT et ADMIN : à élargir si les techniciens/managers doivent aussi voir le bouton.
