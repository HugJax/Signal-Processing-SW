package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

/**
 * Classe ReduceArtefacts
 * Cette classe vise à réduire et supprimer les artefacts d'un signal en corrigeant la ligne de base en la ramenant à 0
 */
public class ReduceArtefacts extends Pretreatment{

    /// CONSTRUCTOR
    public ReduceArtefacts(PretreatmentEnum pretreatmentEnum) {
        super(pretreatmentEnum);
    }

    /// METHODS
    /**
     * Supprime ou réduit les artefacts du signal brut
     * la moyenne des données sur un signal EMG est d'environ de 0
     * cette fonction effectue une correction de la ligne de base en ramenant pour chaque seconde d'enregistrement la moyenne à 0
     */
    public Signal process(Signal signal) {
        int frequency = signal.getSignalFrequency();
        // on sélectionne un timeData à chaque nouvelle seconde de donnée
        for (int timeData = 1; timeData < signal.getDataLength()-1; timeData++) {
            if (timeData % frequency == 0) {
                double totalDataValues = 0;
                int start = timeData-frequency;
                // # on prend une durée de 1 seconde
                for (int timeDataBis = start; timeDataBis < timeData; timeDataBis++) {
                    // on additionne toutes les valeurs dans cette seconde
                    totalDataValues += signal.getData(timeDataBis);
                }
                // on divise la somme totale des valeurs par le nombre de valeur pour trouver une valeur moyenne
                double ratio = totalDataValues/frequency;
                if (Math.abs(ratio) > 0.0001) {
                    // on remplace toutes les valeurs de la seconde par une valeur corrigée donc la valeur précédente
                    // - la valeur moyenne
                    for (int timeDataBis = start; timeDataBis < timeData; timeDataBis++) {
                        signal.setData(timeDataBis,
                                signal.getData(timeDataBis)-ratio);
                    }
                }
            }
        }
        return signal;
    }
}
