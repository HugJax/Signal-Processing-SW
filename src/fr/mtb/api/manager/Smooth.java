package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

/**
 * Classe Smooth est une classe de traitement du signal
 * Application d'une moyenne glissante sur le signal
 * Ce traitement permet de réduire le bruit sur le signal
 */
public class Smooth extends Pretreatment{
    /// ATTRIBUTS
    private double slidingAverage;

    /// CONSTRUCTOR

    /**
     * Création de l'objet Smooth pour le traitement d'un signal
     * @param pretreatmentEnum enum spécifiant le type de traitement
     */
    public Smooth(PretreatmentEnum pretreatmentEnum) {
        super(pretreatmentEnum);
    }

    /// METHODS

    /**
     * Définition de la moyenne glissante à effectuer
     * L'utilisation de cette fonction est obligatoire pour effectuer un traitement du signal
     * @param slidingAverage moyenne glissante
     */
    public void defineSlidingAverage(double slidingAverage) {
        this.slidingAverage = slidingAverage;
    }

    /**
     * Applique une moyenne glissante du signal en prenant la moyenne de t-slidingAverage à t+slidingAverage
     */
    public Signal process(Signal signal) {
        // on applique le traitement dur l'ensemble des données du signal
        for (int timeDataReference = 0; timeDataReference < signal.getDataLength(); timeDataReference++) {
            double totalValue = 0;
            int nbValue = 0;
            // on choisit où commencent les valeurs de référence
            // donc à "sliding average" avant la valeur à modifier
            int startTimeSmooth = (int) (timeDataReference - signal.getSignalFrequency() * this.slidingAverage);
            // s'il n'existe pas de valeur avant on ne prend pas en compte de données avant le point pour le calcul
            if (startTimeSmooth < 0)
                startTimeSmooth = 0;
            // on choisit où terminent les valeurs de référence
            // donc à "sliding average" après la valeur à modifier
            int endTimeSmooth = (int) (timeDataReference + signal.getSignalFrequency() * this.slidingAverage);
            // s'il n'existe pas de valeur après on ne prend pas en compte de données après le point pour le calcul
            if (endTimeSmooth > signal.getDataLength())
                endTimeSmooth = signal.getDataLength();
            // on additionne l'ensemble des valeurs sur cet échantillon et le nombre de points
            for (int timeData = startTimeSmooth; timeData < endTimeSmooth; timeData++) {
                totalValue += signal.getData(timeData);
                nbValue += 1;
            }
            if (nbValue == 0) nbValue = 1;
            // on calcule la valeur moyenne
            double meanValue = totalValue / nbValue;
            // on modifie dans le signal la valeur de la donnée
            signal.setData(timeDataReference, meanValue);
        }
        return signal;
    }
}
