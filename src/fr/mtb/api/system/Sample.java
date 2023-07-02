package fr.mtb.api.system;

import fr.mtb.api.manager.Analysis;

import java.util.ArrayList;

/**
 * Classe Sample
 * Cette classe correspond à un échantillon des enregistrements à étudier
 */
public class Sample {
    /// ATTRIBUTS
    private final Signal signal;
    private ArrayList<Burst> bursts;

    /// CONSTRUCTOR

    /**
     * Création d'un objet échantillon
     * @param signal
     */
    public Sample(Signal signal) {
        this.signal = signal;
    }

    /// METHODS
    public Signal getSignal() {
        return this.signal;
    }

    public ArrayList<Burst> getBurstList() {
        return this.bursts;
    }

    public Analysis getAnalysis() {
        return new Analysis().makeAnalysis(this.signal);
    }

    @Override
    public String toString() {
        Analysis analysis = new Analysis().makeAnalysis(this.signal);
        return "Sample [Start Date: " + analysis.getStartDate() + "; Duration: " + analysis.getDuration() + "; Min Value: " +
                analysis.getMinValue() + "; Max Value: " + analysis.getMaxValue() + "; Average: " + analysis.getMeanValue() + "; " +
                "Area: " +
                analysis.getAreaValue() +
                "]";
    }
}
