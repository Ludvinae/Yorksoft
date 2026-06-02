package com.mns.cda.suivimns.dto.dashboard;

import com.mns.cda.suivimns.dto.dashboard.activity.UserActivity;

import java.util.List;

public record DashboardDirectorDto(
        List<UserActivity> activities
) implements DashboardDto {
}
        /*
    Directeur
    tendances mensuelles
    SLA
    logiciels problématiques
    évolution qualité



    KPIs principaux
    Volume global de tickets
    mois courant
    évolution mensuelle
    SLA respectés

    Exemple :

    92% des tickets traités dans les délais

    Très important.

    Satisfaction client

    Si tu ajoutes un système de notation à la fermeture :

    ⭐ moyenne
    NPS simplifié

    Très crédible dans une soutenance CDA.

    Logiciels les plus problématiques

    Important business.

    Versions les plus instables

    Tu as déjà :

    VERSION
    TYPE_VERSION

    Donc excellent KPI possible.

    Exemple :

    Version	Nombre d’incidents
    2.1.4	53
    2.1.5	4

    Ça donne un côté “pilotage produit”.

    Coût indirect estimé

    Optionnel mais très impressionnant.

    Exemple :

    temps total passé sur incidents
    coût horaire théorique
    Tendance qualité

    Exemple :

    baisse des incidents réseau
    hausse des erreurs utilisateurs
    Graphiques pertinents
    Courbe mensuelle globale
    tickets ouverts
    tickets résolus
    backlog
    Graphique empilé par priorité

    Montre si les incidents critiques augmentent.

    Top 5 logiciels problématiques

    Simple et efficace.

    Evolution du MTTR

    Excellent KPI de maturité support.
     */
