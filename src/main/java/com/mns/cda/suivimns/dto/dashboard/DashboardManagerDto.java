package com.mns.cda.suivimns.dto.dashboard;

import com.mns.cda.suivimns.dto.dashboard.graphs.SoftwareStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TechnicianWorkloadDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.ThemeStatDto;
import com.mns.cda.suivimns.dto.dashboard.graphs.TicketStatusStatDto;

import java.util.List;

public record DashboardManagerDto(
        int openTickets,
        int inProgressTickets,
        int waitingTickets,

        int priorityTickets,
        int overdueTickets,
        int unassignedTickets,

        double averageResolutionTime,
        double averageResponseTime,
        double averageCallDuration,
        double ticketPerTechnician,
        double closedPerDay,
        double closedPerWeek,

        List<TechnicianWorkloadDto> techniciansWorkload,
        List<TicketStatusStatDto> ticketsByStatus,
        List<SoftwareStatDto> ticketsBySoftware,
        List<ThemeStatDto> ticketsByTheme


        /* A ajouter plus tard
        List<TicketEvolutionDto> ticketEvolution
         */

) implements DashboardDto {}

    /*
    Manager
    charge techniciens
    backlog
    tickets par logiciel
    overdue tickets
    temps moyen résolution



    KPIs principaux
    Tickets ouverts globaux

    Avec évolution :

    +12% vs semaine dernière
    Tickets en retard (overdue)

    Très important visuellement :

    badge rouge
    compteur critique
    Répartition de charge par technicien

    Exemple :

    Technicien	Tickets actifs
    Alice	24
    Bob	8

    ➡️ permet de réassigner.

    Taux de résolution par technicien

    Très bon KPI manager.

    Temps moyen de résolution par technicien

    Permet d’identifier :

    experts,
    juniors,
    tickets complexes.
    Tickets sans affectation

    Important pour éviter les oublis.

    Top logiciels problématiques

    Le CDC mentionne explicitement :

    identifier les applications, produits en alerte

    Donc :

    logiciel X → 43 tickets
    logiciel Y → 5 tickets

    Très bon KPI métier.

    Top thématiques de bugs

    Puisque tu as :

    THEMATIQUE
    classification des dysfonctionnements

    Tu peux afficher :

    bugs réseau
    erreurs utilisateur
    lenteurs
    crashs
    Graphiques pertinents
    Heatmap / tableau de charge technicien

    Très pro visuellement.

    Bar chart : tickets par logiciel

    Très utile pour détecter :

    régressions,
    versions instables.
    Courbe temporelle
    ouverts vs fermés
    backlog

    Excellent KPI manager.

    Histogramme : temps moyen de résolution par technicien

    Très parlant.

     */
