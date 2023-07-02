package fr.mtb.api.filesManager;

import fr.mtb.api.manager.Analysis;

import java.io.File;

public class AnalysisFile {
    public enum AnalysisFileEnum {
        TXT("txtFile"), BIN("binFile"), XLS("xlsFile");

        private final String AnalysisFileType;

        AnalysisFileEnum(String AnalysisFileType) {
            this.AnalysisFileType = AnalysisFileType;
        }

        public String toString() {
            return AnalysisFileType;
        }
    }

    /// CONSTRUCTOR
    /**
     * Création de l'objet de gestion du fichier d'une analyse de signal
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param AnalysisFileType type de fichier
     */
    public AnalysisFile(File directory, String filename, AnalysisFileEnum AnalysisFileType) {

    }

    /**
     * Création d'un objet AnalysisFile correspondant au bon format de fichier
     * @param directory chemin d'accès
     * @param filename nom de l'enregistrement
     * @param type type de fichier
     * @return objet de gestion du fichier
     */
    public static AnalysisFile createSignalFile(File directory, String filename, String type) {
        AnalysisFile result = null;
        for (AnalysisFileEnum analysisFileType : AnalysisFileEnum.values()) {
            if (analysisFileType.toString().equals(type)) {
                switch (analysisFileType) {
                    case TXT : {
                        result = new TxtAnalysisFile(directory, filename, analysisFileType);
                        break;
                    }
                    case BIN : {
                        result = new BinAnalysisFile(directory, filename, analysisFileType);
                        break;
                    }
                    case XLS : {
                        result = new XlsAnalysisFile(directory, filename, analysisFileType);
                        break;
                    }
                    default :
                }
            }
        }
        return result;
    }

    public Analysis read() {
        return null;
    }

    public void write(Analysis analysis) {

    }
}
