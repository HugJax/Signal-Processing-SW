package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;
import fr.mtb.api.util.Date;

import java.sql.Time;
import java.util.Collections;

/**
 * Classe Analysis
 * cette classe permet une analyse global d'un signal
 */
public class Analysis {
    /// ATTRIBUTS
    private Date startDate;
    private Time duration;
    private double minValue;
    private double maxValue;
    private double meanValue;
    private double areaValue;

    /// CONSTRUCTOR
    public Analysis() {

    }

    /**
     * Constructeur lorsque l'on connait tous les paramètres
     * @param startDate date de début
     * @param duration durée
     * @param minValue valeur minimale
     * @param maxValue valeur maximale
     * @param meanValue valeur moyenne
     * @param areaValue aire sous la courbe
     */
    public Analysis(Date startDate, Time duration, double minValue, double maxValue, double meanValue,
                    double areaValue) {
        this.startDate = startDate;
        this.duration = duration;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.meanValue = meanValue;
        this.areaValue = areaValue;
    }

    /**
     * Création de l'analyse d'un signal
     * @param signal signal à analyser
     * @return analyse du signal
     */
    public Analysis makeAnalysis(Signal signal) {
        Date startDate = signal.getDate(0);
        Time duration = new Date().getTimeDifferenceBetweenDates(signal.getDate(0),
                signal.getDate(signal.getDataLength()-1));
        double minValue = signal.getDataList().get(signal.getDataList().indexOf(Collections.min(signal.getDataList())));
        double maxValue = signal.getDataList().get(signal.getDataList().indexOf(Collections.max(signal.getDataList())));
        double areaValue = 0;
        for (int i = 1; i < signal.getDataLength(); i++) {
            areaValue += signal.getData(i)/ signal.getSignalFrequency();
            areaValue += (Math.abs(signal.getData(i-1) - signal.getData(i))/signal.getSignalFrequency())/2;
        }
        float somme = 0;
        for (Double emgValuePoint : signal.getDataList()) {
            somme += emgValuePoint;
        }
        double meanValue = somme / signal.getDataLength();

        return new Analysis(startDate, duration, minValue, maxValue, meanValue, areaValue);
    }

    public Date getStartDate() {
        return startDate;
    }

    public Time getDuration() {
        return duration;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMeanValue() {
        return meanValue;
    }

    public double getAreaValue() {
        return areaValue;
    }
}
