package fr.mtb.api.filesManager;

import fr.mtb.api.manager.Analysis;
import fr.mtb.api.system.Signal;
import fr.mtb.api.util.Date;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe TxtSignalFile
 * Cette classe permet de lire et d'écrire des fichiers texte correspondant à un signal
 */
public class TxtSignalFile extends SignalFile {
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
    public TxtSignalFile(File directory, String filename, SignalFileEnum signalFileType) {
        super(directory, filename, signalFileType);
        this.directory = directory;
        this.filename = filename;
    }

    /// METHODS

    /**
     * Lecture d'un fichier texte d'un signal au format MTB-2
     * @param signalEnum type de signal
     * @return signal chargé
     */
    public Signal read(Signal.SignalEnum signalEnum) {
        try (BufferedReader fileBufferReading =
                     new BufferedReader(new FileReader(this.directory + "/" + this.filename + ".txt"))) {
            String line;
            String[] splittedLine;
            int compteurDeLigne = 0;
            ArrayList<Date> datesPoints = new ArrayList<>();
            ArrayList<Double> dataPoints = new ArrayList<>();
            while ((line = fileBufferReading.readLine()) != null) {
                if (compteurDeLigne > 8) {
                    splittedLine = line.split("\t");
                    Date pointDate = new Date(splittedLine[0]);
                    double pointData = 0;
                    if (!splittedLine[1].equals("nan"))
                        pointData = Double.parseDouble(splittedLine[1]);
                    datesPoints.add(pointDate);
                    dataPoints.add(pointData);
                }
                compteurDeLigne += 1;
            }
            fileBufferReading.close();
            return new Signal(datesPoints, dataPoints, Signal.SignalEnum.RAW);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ecriture du signal dans un fichier au format MTB-2
     * @param signal signal à écrire
     * @param signalEnum type de signal
     */
    public void write(Signal signal, Signal.SignalEnum signalEnum) {
        Analysis analysis = new Analysis().makeAnalysis(signal);
        String pathString = this.directory + "/export/" + this.filename;
        Path path = Paths.get(pathString);
        try {
            // Vérification de l'existence du chemin d'accès ou le crée s'il n'existe pas
            Files.createDirectories(path);
            File filePath = new File(pathString + "/processedSignal_MTB.txt");
            PrintWriter processedSignalFile = new PrintWriter(filePath);

            // Ajout du header
            String header = "MTB-2\n";
            header += "Saved Data: \tTransmitter\n";
            header += "Cage n\u00b0 \tS1\n"; // la cage n'est pas la bonne et reste la même pour toutes les exportations
            // pour le moment, à voir si nécessité de changer
            Date startDate = analysis.getStartDate();
            String startTime = startDate.getHours() + ":" + startDate.getMinutes() + ":" + startDate.getSeconds();
            header += "Start Recording Date: \t" + analysis.getStartDate().toStringDateFormat() + "\n";
            header += "Start Recording Time: \t" + startTime + "\n" +
                    "\n\n";
            header += "Time\t\t\t\t\tEMG | Temperature\n";
            header += "YYYY-MM-DD H:min:sec:ms\t[mV] | [\u00b0C]\n";
            processedSignalFile.write(header);

            // Écriture du signal
            for (int i=0; i<signal.getDataList().size(); i++) {
                processedSignalFile.write((signal.getDate(i) + "\t" + Math.round(signal.getData(i) * 100000d) / 100000d +
                        "\n"));
            }

            processedSignalFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
