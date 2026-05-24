package com.mns.cda.suivimns.service.business;

import org.springframework.stereotype.Component;

@Component
public class PriorityCalculator {

    public Integer computePriority(int impactFactor, int urgencyFactor, int importance, int malus) {
        return calcul(adjustImpact(impactFactor, importance),adjustUrgency(urgencyFactor, malus));
    }

    private Integer calcul(double adjustedImpact, double adjustedUrgency) {
        return (int) ((adjustedImpact * 70) + (adjustedUrgency * 30));
    }



    /**
     * Ajuste l'impact en fonction de l'importance du client
     * Toujours compris entre 0 (impact bas) et 3 (impact max)
     * @param impact
     * @param importance
     * @return
     */
    private double adjustImpact(int impact, int importance) {
        int adjusted = impact + importance;

        adjusted = Math.max(0, Math.min(adjusted, 3));

        return adjusted / 3.0;
    }

    /**
     * Ajuste l'urgence en fonction du type de version du logiciel concerné
     * Toujours compris entre 0 (urgence faible) et 2 (urgence critique)
     * @param urgency
     * @param malus
     * @return
     */
    private double adjustUrgency(int urgency, int malus) {
        int adjusted = urgency - malus;

        adjusted = Math.max(0, Math.min(adjusted, 2));

        return adjusted / 2.0;
    }
}
