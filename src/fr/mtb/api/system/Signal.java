package fr.mtb.api.system;

import fr.mtb.api.manager.Analysis;
import fr.mtb.api.util.Date;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe Signal
 * Le signal créé peut être brut, mais également prétraité
 * Il est constitué d'une liste de date et d'une liste de données EMG, chaque donnée EMG étant associé à une date en
 * absolue
 */
public class Signal {
    /// ATTRIBUTS
    private int id;
    private final SignalEnum signalType;
    private final ArrayList<Date> dates;
    private final ArrayList<Double> dataValue;

    public enum SignalEnum {
        RAW("rawData"), PROCESSED("processedData");

        private final String signalType;

        SignalEnum(String signalType) {
            this.signalType = signalType;
        }

        public String toString() {
            return signalType;
        }
    }

    /// CONSTRUCTOR
    /**
     * Constructeur d'un signal
     * @param signalType type de signal, soit brut soit traité
     */
    public Signal(ArrayList<Date> date, ArrayList<Double> dataValue, SignalEnum signalType) {
        this.signalType = signalType;
        this.dates = date;
        this.dataValue = dataValue;
    }

    /// METHODS
    /**
     * Duplicate the signal
     */
    public Signal duplicate() {
        return new Signal(this.dates, this.dataValue, this.signalType);
    }

    /**
     * Renvoies la fréquence du signal
     * @return signalFrequency
     */
    public int getSignalFrequency() {
        double period = Math.abs(this.dates.get(2).getMilliseconds() -
                this.dates.get(1).getMilliseconds()) * 0.001;
        if (period == 0) period = 0.0002;
        return (int) (1 / period);
    }

    public int getDataLength() {
        return this.dataValue.size();
    }

    /**
     * Retourne le paramètre de date
     * @return date
     */
    public Date getDate(int num) {
        return this.dates.get(num);
    }

    /**
     * Retourne le paramètre d'amplitude
     * @return amplitude EMG
     */
    public double getData(int num) {
        return this.dataValue.get(num);
    }

    /**
     * Permet la modification de la donnée dans le signal
     * @param num position de la valeur à modifier
     * @param dataValue nouvelle valeur de la donnée
     */
    public void setData(int num, double dataValue) {
        this.dataValue.set(num, dataValue);
    }

    /**
     * Retourne l'ensemble de la liste des données du signal
     * @return dataValue
     */
    public ArrayList<Double> getDataList() {
        return this.dataValue;
    }

    /**
     * Retourne l'ensemble de la liste des dates du signal
     * @return datesValue
     */
    public ArrayList<Date> getDatesList() {
        return this.dates;
    }
}
