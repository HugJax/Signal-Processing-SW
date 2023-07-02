package fr.mtb.api.filesManager;

import fr.mtb.api.system.Signal;
import fr.mtb.api.util.Date;

import java.io.File;
import java.util.ArrayList;


import br.unb.biologiaanimal.edf.*;

public class EdfSignalFile extends SignalFile{
    /// ATTRIBUTS
    private final File directory;
    private final String filename;

    /// CONSTRUCTOR

    /**
     * Création de l'objet de gestion des fichiers textes
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param signalFileType type de fichier
     */
    public EdfSignalFile(File directory, String filename, SignalFileEnum signalFileType) {
        super(directory, filename, signalFileType);
        this.directory = directory;
        this.filename = filename;
    }

    // lien github package : https://github.com/lab-neuro-comp/EDF

    /**
     * Lecture d'un fichier EDF pour créer un objet signal
     *
     * @param signalEnum type de fichier
     * @return signal
     */
    public Signal read(Signal.SignalEnum signalEnum) {
        double frequency = 5000;
        EDF edfFile = new EDF(this.directory + "/" + this.filename + ".edf");
        String[] startDateString = edfFile.getHeader().get("startdate").toString().split("\\.");
        String[] startTimeString = edfFile.getHeader().get("starttime").toString().split("\\.");
        Date startDate = new Date(Integer.parseInt(startDateString[0]), Integer.parseInt(startDateString[1]),
                Integer.parseInt(startDateString[2]) + 2000, Integer.parseInt(startTimeString[0]),
                Integer.parseInt(startTimeString[1]), Integer.parseInt(startTimeString[2]));

        double[] emgValues = edfFile.getSignal("EMG");
        ArrayList<Date> dates = new ArrayList<>(emgValues.length);
        ArrayList<Double> data = new ArrayList<>(emgValues.length);

        for (double emgValue : emgValues) {
            startDate.addTimeInMilliseconds((long) (1 / frequency) * 1000);
            dates.add(startDate);
            data.add(emgValue);
        }
        return new Signal(dates, data, signalEnum);
    }
}
