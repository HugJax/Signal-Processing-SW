package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;
import fr.mtb.api.util.Date;

public class BurstManager {

    public BurstManager() {

    }

    /**
     * Détection des bursts par dépassement de seuil
     * @param risingThreshold seuil montant devant être dépassé
     * @param risingHysteresis seuil minimal par lequel le signal doit passer pour considérer un burst
     * @param delay durée minimale du burst
     * @param fallingThreshold seuil descendant à dépasser
     */
    private Signal burstDetection(Signal processedSignal, double risingThreshold, double risingHysteresis, double delay,
                                  double fallingThreshold) {
        Date startBurst;
        Date endBurst;
        for (int num = 0; num < processedSignal.getDataLength(); num++) {
            // si aucun burst en cours et qu'il y a dépassement de seuil alors startBurst
            // vérifier que le seuil - hysteresis est passé avant de valider
            // si burst en cours et qu'il y a dépassement de seuil alors fin de burst
            // vérifier qu'on passe bien par seuil - hysteresis

            // on obtient date début et date fin
            // on vérifie que le burst fait au moins la durée du délai
            // si c'est validé on ajoute le burst à la liste de bursts du signal
        }
        return processedSignal;
    }
}
