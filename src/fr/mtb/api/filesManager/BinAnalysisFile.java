package fr.mtb.api.filesManager;

import fr.mtb.api.manager.Analysis;
import fr.mtb.api.util.Date;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Classe BinAnalysisFile
 * classe permettant l'écriture et la lecture de fichiers d'analyse en binaire
 */
public class BinAnalysisFile extends AnalysisFile {
    /// ATTRIBUTS
    private final File directory;
    private final String filename;

    /// CONSTRUCTOR
    /**
     * Création de l'objet de gestion des fichiers binaires
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param AnalysisFileType type de fichier
     */
    public BinAnalysisFile(File directory, String filename, AnalysisFileEnum AnalysisFileType) {
        super(directory, filename, AnalysisFileType);
        this.directory = directory;
        this.filename = filename;
    }

    /**
     * Lecture d'une analyse au format binaire
     * @return analyse
     */
    public Analysis read() {
        return new Analysis();
    }

    /**
     * Ecriture d'un fichier d'analyse au format binaire
     * @param analysis analyse
     */
    public void write(Analysis analysis) {
        String pathString = this.directory + "/binary/" + this.filename + "/analysis/";
        Path path = Paths.get(pathString);
        try {
            // Vérification de l'existence du chemin d'accès ou le crée s'il n'existe pas
            Files.createDirectories(path);
            File filePath = new File(pathString + "/analysis_all");
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
            processedSignalFile.write(Arrays.toString(analysisTxt.getBytes()));

            processedSignalFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
