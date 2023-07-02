package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

/**
 * Classe SubSampling
 * cette classe permet un sous-échantillonnage du signal
 */
public class SubSampling extends Pretreatment{
    /// ATTRIBUTS
    private int frequency;

    /// CONSTRUCTOR
    /**
     * Création d'un objet rectifiy pour le traitement du signal
     * @param pretreatmentEnum enum spécifiant le type de traitement
     */
    public SubSampling(PretreatmentEnum pretreatmentEnum) {
        super(pretreatmentEnum);
    }

    /// METHODS
    /**
     * Passe toutes les valeurs du signal brut dans le positif et créé le signal traité
     */
    public Signal process(Signal signal) {
        double period = (double) 1 / this.frequency;
        for (int timeData = 0; timeData < signal.getDataList().size(); timeData++) {
            if (signal.getDate(timeData).getMilliseconds()%period != 0) {
                signal.getDataList().remove(timeData);
                signal.getDatesList().remove(timeData);
            }
        }
        return signal;
    }

    /**
     * Définition de la nouvelle fréquence à appliquer sur le signal
     * Cette méthode est obligatoire pour l'utilisation du prétraitement
     * @param frequency fréquence
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
