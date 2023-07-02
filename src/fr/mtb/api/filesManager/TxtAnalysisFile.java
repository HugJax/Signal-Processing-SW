package fr.mtb.api.filesManager;

import fr.mtb.api.manager.Analysis;
import fr.mtb.api.util.Date;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Classe TxtAnalysisFile
 * cette classe permet l'écriture d'un fichier d'analyse au format texte
 */
public class TxtAnalysisFile extends AnalysisFile {
    /// ATTRIBUTS
    private final File directory;
    private final String filename;

    /// CONSTRUCTOR
    /**
     * Création de l'objet de gestion des fichiers textes
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param AnalysisFileType type de fichier
     */
    public TxtAnalysisFile(File directory, String filename, AnalysisFileEnum AnalysisFileType) {
        super(directory, filename, AnalysisFileType);
        this.directory = directory;
        this.filename = filename;
    }

    /**
     * Ecriture d'un fichier analyse au format texte
     * @param analysis analyse
     */
    public void write(Analysis analysis) {
        String pathString = this.directory + "/export/" + this.filename;
        Path path = Paths.get(pathString);
        try {
            // Vérification de l'existence du chemin d'accès ou le crée s'il n'existe pas
            Files.createDirectories(path);
            File filePath = new File(pathString + "/analysis_all.txt");
            PrintWriter processedSignalFile = new PrintWriter(filePath);

            // Ajout du header
            String analysisTxt = "Saving name: \t" + this.filename + "\n";
            Date startDate = analysis.getStartDate();
            String startTime = startDate.getHours() + ":" + startDate.getMinutes() + ":" + startDate.getSeconds();
            analysisTxt += "Start Recording Date: \t" + analysis.getStartDate().toStringDateFormat() + "\n";
            analysisTxt += "Start Recording Time: \t" + startTime + "\n";
            analysisTxt += "Saving Duration: \t" + analysis.getDuration() + "\n";
            analysisTxt += "Minimum Value: \t" + analysis.getMinValue() + "\n";
            analysisTxt += "Maximum Value: \t" + analysis.getMaxValue() + "\n";
            analysisTxt += "Mean Value: \t" + analysis.getMeanValue() + "\n";
            analysisTxt += "Area Value: \t" + analysis.getAreaValue() + "\n";
            processedSignalFile.write(analysisTxt);

            processedSignalFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
