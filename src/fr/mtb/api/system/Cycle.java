package fr.mtb.api.system;

import fr.mtb.api.util.Date;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Classe Cycle
 * La classe cycle correspond au cycle choisit pour étudier les enregistrements d'une session
 * À partir d'un cycle sont déterminés des échantillons qui vont être analysés
 */
public class Cycle {
    /// ATTRIBUTS
    private final Date startDate;
    private final Time period;
    private final Time sampleDuration;

    /// CONSTRUCTOR

    /**
     * Création d'un objet cycle
     * @param startDate date de début
     * @param period période d'un cycle
     * @param sampleDuration durée d'un échantillon
     */
    public Cycle(Date startDate, Time period, Time sampleDuration) {
        this.startDate = startDate;
        this.period = period;
        this.sampleDuration = sampleDuration;
    }

    /// METHODS

    /**
     * Création d'une liste d'échantillon à partir d'un signal
     * @param signal signal à échantillonner
     * @return liste des échantillons
     */
    public ArrayList<Sample> createSamples(Signal signal) {
        ArrayList<Sample> samples = new ArrayList<>();
        Date endSignalDate = signal.getDate(signal.getDataLength() - 1);
        long totalTime = endSignalDate.getTimeInMillis() - this.startDate.getTimeInMillis();
        long period =
                this.period.getSeconds() * 1000 + this.period.getMinutes() * 1000 * 60 + this.period.getHours() * 1000 * 60 * 60;
        int nbCycles = (int) (totalTime / period + 1);
        for (int i = 0; i < nbCycles; i++) {
            Date start = (Date) this.startDate.clone();
            start.addTimeInMilliseconds(i * period);
            Date endCycleDate = new Date(start.getDay(), start.getMonth(), start.getYear(),
                    (start.getHours() + this.period.getHours()), (start.getMinutes() + this.period.getMinutes()), (start.getSeconds() + this.period.getSeconds()));
            ArrayList<Date> datesPoints = new ArrayList<>();
            ArrayList<Double> dataPoints = new ArrayList<>();
            for (int num = 0; num < signal.getDataLength(); num++) {
                int diffStartData = start.compareTo(signal.getDate(num));
                int diffEndData = endCycleDate.compareTo(signal.getDate(num));
                if (diffStartData <= 0 && diffEndData >= 0) {
                    datesPoints.add(signal.getDate(num));
                    dataPoints.add(signal.getData(num));
                }
            }
            if (datesPoints.size() > 5) {
                Sample sample = new Sample(new Signal(datesPoints, dataPoints, Signal.SignalEnum.PROCESSED));
                samples.add(sample);
            }
        }
        return samples;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Time getPeriod() {
        return period;
    }

    public Time getSampleDuration() {
        return sampleDuration;
    }
}
