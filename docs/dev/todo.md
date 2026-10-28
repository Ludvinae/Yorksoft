# TODO — Staging for Trello

The backlog lives on the project's Trello dashboard (board "Yorksoft"), not in this repo. This file is just a scratch pad: draft a card here first, then move it into Trello and delete the entry — don't let items accumulate here long-term.

## Procédures sur la page Base de connaissances

- Pouvoir créer, modifier et supprimer les procédures d'un article directement depuis la page Base de connaissances (aujourd'hui elles sont seulement affichées en lecture).
- Le backend a déjà les routes `/procedure` (création/modification par technicien, modification limitée à l'auteur ou un admin, suppression admin) : il reste surtout le frontend (formulaire, boutons, rafraîchissement de l'article) et à vérifier les droits côté UI.
- Bonus possible : créer un nouvel article depuis cette page (aujourd'hui uniquement depuis la fiche ticket).

## Page « Utilisateurs » pour l'admin

- Il n'existe aucune liste des employés ni de l'ensemble des comptes : seulement la liste des clients (`/clients`) et la création d'utilisateur côté admin. Or les KPI de la rangée « Utilisateurs » du dashboard admin concernent surtout des employés (sans rôle, rôles multiples, mot de passe non initialisé, employés inactifs / jamais actifs, comptes admin).
- Créer une page admin listant tous les comptes avec leur(s) rôle(s), avec une route backend paginée et filtrable (un filtre par KPI, sur le modèle de `TicketSearchCriteria` / `TicketSpecification` — attention, le rôle se lit dans les tables des sous-types, cf. les requêtes natives de `AppUserDao`).
- Puis rendre les cartes « Utilisateurs » cliquables : `kpi-card` émet déjà `cardClick`, il suffit que le dashboard admin navigue vers cette page avec le bon filtre.

## Page d'édition des clients

- Ajouter une page d'édition des clients, accessible via un bouton depuis la liste des clients (`pages/client/client-list`).
- Le composant `pages/client/client-edit` existe déjà mais n'est qu'un squelette généré (« client-edit works! »), sans route : il reste à le brancher et à construire le formulaire.
- Côté backend, `PATCH /client/{id}` existe déjà : mais `@IsClient` n'autorise que CLIENT et ADMIN : à élargir si les techniciens/managers doivent aussi voir le bouton.

## Base de connaissances : affichage en liste sur desktop

- Sur desktop, remplacer l'affichage en cartes de la page Base de connaissances (`pages/knowledge`) par un format liste.
- Garder les cartes sur mobile, sur le modèle des listes de tickets qui ont déjà des composants desktop et mobile distincts (`components/list/desktop-table` / `mobile-card-list`).

## Filtres du dashboard écrasés par « masquer les tickets clos »

- Quand l'utilisateur a masqué les tickets clos dans la liste, `TicketStoreService.effectiveFilters` force `statusesExcluded = ['CLOSED']`, y compris sur les filtres posés par une carte KPI du dashboard.
- Conséquence : la carte admin « Fermés sans date de fin » (`statuses: ['CLOSED']`) ouvre une liste vide. Les filtres venant d'une carte KPI devraient passer outre cette préférence.

## Tâche planifiée de recalcul du SLA

- Aujourd'hui `overdue` et `slaDeadline` ne sont recalculés que lors d'une action sur le ticket (`TicketMetricsService.refreshTicketMetrics`, appelé par `TicketService`) : un ticket laissé sans action après son échéance garde `overdue = false`.
- Ajouter un `@Scheduled` (ex. toutes les 5 min) qui recalcule les métriques des tickets où le SLA court (OPEN, ASSIGNED, IN_PROGRESS).
- Effets : la carte « En retard » du dashboard manager devient fiable, et le KPI admin « SLA dépassé non signalé » ne devrait plus dépasser 0 que si la tâche ne tourne plus.
- Attention au bug connu de décalage de ~2h : `SlaService` utilise `LocalDateTime.now()` (fuseau de la JVM).
