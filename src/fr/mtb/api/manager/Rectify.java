package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

/**
 * Classe Rectify permettant une rectification du signal
 * Cette méthode consiste à passer toutes les valeurs du signal dans le positif en inversant les valeurs négatives
 */
public class Rectify extends Pretreatment{

    /// CONSTRUCTOR

    /**
     * Création d'un objet rectifiy pour le traitement du signal
     * @param pretreatmentEnum enum spécifiant le type de traitement
     */
    public Rectify(PretreatmentEnum pretreatmentEnum) {
        super(pretreatmentEnum);
    }

    /// METHODS
    /**
     * Passe toutes les valeurs du signal brut dans le positif et créé le signal traité
     */
    public Signal process(Signal signal) {
        for (int timeData = 0; timeData < signal.getDataList().size(); timeData++) {
            signal.setData(timeData, Math.abs(signal.getData(timeData)));
        }
        return signal;
    }
}
