package com.mns.cda.suivimns.dto.dashboard;

public record DashboardAdminDto(
        int closedTicketsWithoutEndDate
) implements DashboardDto {
}
    /*
    Les meilleurs KPIs admin pour ton projet

    tickets clos sans date
    tickets ouverts avec date close
    tickets sans affectation
    licences expirées
    utilisateurs inactifs
    erreurs backend
    anomalies SLA
    score intégrité données



    Tickets ouverts avec date de clôture

    Autre incohérence métier.

    Tickets sans historique actif

    Exemple :

    ticket créé
    mais aucun statut courant
    Tickets sans affectation depuis X heures

    Très pertinent opérationnellement.

    Affectations invalides

    Exemple :

    date_fin < date_affectation
    Priorité incohérente

    Exemple :

    impact critique
    urgence faible
    priorité finale basse

    Tu peux détecter des anomalies métier intéressantes.

    Commentaires orphelins

    Exemple :

    commentaire sans ticket valide
    employé supprimé
    Monitoring système

    Très crédible pour un admin.

    Temps moyen des requêtes API

    Même simulé/statique.

    Nombre d’erreurs backend

    Exemple :

    erreurs 500
    exceptions métiers
    Dernier refresh des métriques

    Très utile si tu calcules des aggregates.

    Volume de notifications échouées

    Si tu as :

    emails,
    notifications,
    websocket plus tard.
    Qualité de données

    Très impressionnant en soutenance.

    Clients sans organisation
    Licences expirées encore actives

    Tu as déjà la table :
    LICENCE

    Donc excellent KPI.

    Versions sans tickets depuis longtemps

    Peut révéler :

    logiciel abandonné,
    données obsolètes.
    Sécurité / conformité

    Vu le CDC :

    RGPD
    anonymisation
    cryptage

    Tu peux même ajouter :

    Comptes inactifs
    Sessions expirées
    Utilisateurs sans rôle valide
     */
