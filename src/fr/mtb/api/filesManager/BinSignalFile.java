package fr.mtb.api.filesManager;

import fr.mtb.api.system.Signal;
import fr.mtb.api.util.Date;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe BinSignalFile
 * Cette classe permet de lire des fichiers de signaux bruts ou prétraités ou d'en écrire au format binaire
 */
public class BinSignalFile extends SignalFile{
    /// ATTRIBUTS
    private final File directory;
    private final String filename;

    /// CONSTRUCTOR

    /**
     * Création de l'objet de gestion de fichier signaux en binaires
     * @param directory chemin d'accès de la session
     * @param filename nom du fichier
     * @param signalFileType type d'enregistrement
     */
    public BinSignalFile(File directory, String filename, SignalFileEnum signalFileType) {
        super(directory, filename, signalFileType);
        this.directory = directory;
        this.filename = filename;
    }

    /// METHODS

    /**
     * Lecture d'un fichier signal
     * @param signalEnum type de signal (brut ou prétraité)
     * @return signal
     */
    public Signal read(Signal.SignalEnum signalEnum) {
        // Création du chemin pour accéder au dossier contenant les fichiers binaires
        String pathString = this.directory + "/binary/" + this.filename + "/signals";

        try {
            // création du lecteur
            FileInputStream binaryFile = null;
            switch (signalEnum) {
                case RAW : {
                    binaryFile = new FileInputStream(pathString + "/" + Signal.SignalEnum.RAW);
                    break;
                }
                case PROCESSED : {
                    binaryFile = new FileInputStream(pathString + "/" + Signal.SignalEnum.PROCESSED);
                    break;
                }
                default :
            }

            // Ouverture du fichier et conversion du binaire à la chaine de caractère
            InputStreamReader reader = new InputStreamReader(binaryFile);
            int bufferSize = 1024;
            char[] buffer = new char[bufferSize];
            StringBuilder out = new StringBuilder();
            for (int numRead; (numRead = reader.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }

            // Mise en forme pour la création d'un signal
            String outData = out.toString();
            ArrayList<Date> datePoints = new ArrayList<>();
            ArrayList<Double> dataPoints = new ArrayList<>();
            String[] splittedData = outData.split("\n");
            for (String line : splittedData) {
                String[] splittedLine = line.split("\t");
                datePoints.add(new Date(splittedLine[0]));
                dataPoints.add(Double.parseDouble(splittedLine[1]));
            }

            reader.close();

            // Création du signal
            Signal signal = null;
            switch (signalEnum) {
                case RAW : {
                    signal = new Signal(datePoints, dataPoints, Signal.SignalEnum.RAW);
                    break;
                }
                case PROCESSED : {
                    signal = new Signal(datePoints, dataPoints, Signal.SignalEnum.PROCESSED);
                    break;
                }
                default :
            }
            return signal;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Écriture du fichier d'un signal en binaire
     * @param signal signal à écrire
     * @param signalEnum type de signal
     */
    public void write(Signal signal, Signal.SignalEnum signalEnum) {
        String pathString = this.directory + "/binary/" + this.filename + "/signals";
        Path path = Paths.get(pathString);

        try {
            // création des dossiers si inexistants
            Files.createDirectories(path);
            // création du fichier binaire
            FileOutputStream binaryFile = null;
            switch (signalEnum) {
                case RAW : {
                    binaryFile = new FileOutputStream(pathString + "/" + Signal.SignalEnum.RAW);
                    break;
                }
                case PROCESSED : {
                    binaryFile = new FileOutputStream(pathString + "/" + Signal.SignalEnum.PROCESSED);
                    break;
                }
                default :
            }
            // écriture du fichier binaire
            for (int num = 0; num < signal.getDataLength(); num++) {
                binaryFile.write((signal.getDate(num) + "\t" + Math.round(signal.getData(num) * 100000d) / 100000d +
                        "\n").getBytes());
            }

            binaryFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
