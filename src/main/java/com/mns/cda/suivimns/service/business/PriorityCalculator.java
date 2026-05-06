package com.mns.cda.suivimns.service.business;

import com.mns.cda.suivimns.enumerate.Priority;
import org.springframework.stereotype.Component;

@Component
public class PriorityCalculator {

    public Priority computePriority(int impact, int urgency, int importance, int malus) {
        return priorityMatrix
                [adjustImpact(impact, importance)]
                [adjustUrgency(urgency, malus)];
    }

    private static final Priority[][] priorityMatrix =
            {{Priority.VERY_LOW, Priority.LOW},
                    {Priority.LOW, Priority.MEDIUM},
                    {Priority.MEDIUM, Priority.HIGH},
                    {Priority.HIGH, Priority.VERY_HIGH}};

    /**
     * Ajuste l'impact en fonction de l'importance du client - 1
     * Toujours compris entre 0 (impact bas) et 3 (impact max)
     * Prends en compte l'ajustement pour l'index de la matrice
     * @param impact
     * @param importance
     * @return
     */
    private int adjustImpact(int impact, int importance) {
        int adjusted = impact + importance - 1;

        adjusted = Math.max(1, Math.min(adjusted, 4));

        return adjusted - 1;
    }

    /**
     * Ajuste l'urgence en fonction du type de version du logiciel concerné
     * Toujours compris entre 0 (urgence faible) et 1 (urgence élevée)
     * Prends en compte l'ajustement pour l'index de la matrice
     * @param urgency
     * @param malus
     * @return
     */
    private int adjustUrgency(int urgency, int malus) {
        int adjusted = urgency - malus;

        adjusted = Math.max(1, Math.min(adjusted, 2));

        return adjusted - 1;
    }
}
