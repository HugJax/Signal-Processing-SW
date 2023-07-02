package fr.mtb.api.filesManager;

import fr.mtb.api.manager.Analysis;
import fr.mtb.api.system.Cycle;
import fr.mtb.api.system.Sample;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XlsAnalysisFile extends AnalysisFile {
    /// ATTRIBUTS
    private final File directory;
    private final String filename;

    /// CONSTRUCTOR
    /**
     * Création de l'objet de gestion des fichiers xls
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param AnalysisFileType type de fichier
     */
    public XlsAnalysisFile(File directory, String filename, AnalysisFile.AnalysisFileEnum AnalysisFileType) {
        super(directory, filename, AnalysisFileType);
        this.directory = directory;
        this.filename = filename;
    }

    /**
     * Ecriture du fichier d'analyse de cycle de tous les échantillons au format xls
     * @param cycle cycle choisi
     * @param samples échantillons créés
     */
    public void writeCycleAnalysis(Cycle cycle, ArrayList<Sample> samples) {
        try {
            // création de la feuille tableur
            WritableWorkbook workbook = Workbook.createWorkbook(new File(this.directory + "/" + this.filename));

            // écriture du header
            WritableSheet cycleAnalysisSheet = workbook.createSheet("Cycle Analysis", 0);
            cycleAnalysisSheet.addCell(new Label(0, 0, "Parameters"));
            cycleAnalysisSheet.addCell(new Label(0, 1, "Start Date Cycle Analysis"));
            cycleAnalysisSheet.addCell(new Label(1, 1, cycle.getStartDate().toStringCompleteDateFormat()));
            cycleAnalysisSheet.addCell(new Label(0, 2, "period Cycle Analysis"));
            cycleAnalysisSheet.addCell(new Label(1, 2, cycle.getPeriod().toString()));
            cycleAnalysisSheet.addCell(new Label(0, 3, "Sample Duration Cycle Analysis"));
            cycleAnalysisSheet.addCell(new Label(1, 3, cycle.getSampleDuration().toString()));

            // écriture des titres
            cycleAnalysisSheet.addCell(new Label(0, 5, "Start Date"));
            cycleAnalysisSheet.addCell(new Label(1, 5, "Duration"));
            cycleAnalysisSheet.addCell(new Label(2, 5, "Minimum Value"));
            cycleAnalysisSheet.addCell(new Label(3, 5, "Maximum Value"));
            cycleAnalysisSheet.addCell(new Label(4, 5, "Mean Value"));
            cycleAnalysisSheet.addCell(new Label(5, 5, "Area Value"));

            // écriture des statistiques des cycles
            int rowIndex = 6; // première ligne d'écriture d'un échantillon
            for (Sample sample : samples) {
                if (sample.getSignal() != null) {
                    Analysis analysis = sample.getAnalysis();
                    cycleAnalysisSheet.addCell(new Label(0, rowIndex, analysis.getStartDate().toStringCompleteDateFormat()));
                    cycleAnalysisSheet.addCell(new Label(1, rowIndex, analysis.getDuration().toString()));
                    cycleAnalysisSheet.addCell(new Label(2, rowIndex, Double.toString(analysis.getMinValue())));
                    cycleAnalysisSheet.addCell(new Label(3, rowIndex, Double.toString(analysis.getMaxValue())));
                    cycleAnalysisSheet.addCell(new Label(4, rowIndex, Double.toString(analysis.getMeanValue())));
                    cycleAnalysisSheet.addCell(new Label(5, rowIndex, Double.toString(analysis.getAreaValue())));
                    rowIndex += 1;
                }
            }

            workbook.write();
            workbook.close();
        } catch (WriteException | IOException e) {
            System.out.println("Une erreur est survenue");
        }
    }
}
