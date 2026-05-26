package com.mns.cda.suivimns.dto.dashboard;

import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;

import java.util.List;

public record DashboardTechnicianDto(
    int assignedOpenTickets,
    int assignedWaitingTickets,
    int assignedCriticalTickets,
    int assignedOverdueTickets,

    double meanTimeToSolveTickets


) implements DashboardDto {}

    /*
    Technicien
    cartes KPI
    tickets par statut
    tickets critiques
    activité récente



    Temps moyen de résolution personnel
    MTTR (Mean Time To Resolution)

    Exemple :

    aujourd’hui
    cette semaine
    ce mois
    Temps moyen de première réponse

    Très pertinent pour un support.

    Taux de résolution

    Exemple :

    tickets résolus / tickets pris en charge
    Charge de travail actuelle

    Exemple :

    nombre de tickets actifs
    répartition par priorité
    Graphiques pertinents
    Camembert : répartition des tickets
    critique
    haute
    moyenne
    basse
    Bar chart : tickets par statut
    ouvert
    assigné
    en attente client
    résolu
    Courbe : activité sur 7 jours
    tickets créés
    tickets fermés

    Très utile pour donner une vision de progression.
     */
