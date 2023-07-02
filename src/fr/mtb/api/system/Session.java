package fr.mtb.api.system;

import fr.mtb.api.filesManager.AnalysisFile;
import fr.mtb.api.filesManager.XlsAnalysisFile;

import java.io.File;
import java.util.ArrayList;

/**
 * Classe Session regroupe tous les enregistrements à étudier ensemble
 * Il est possible de créer une nouvelle session à partir de fichiers au format MTB-2 positionnés à la racine de la
 * session ou d'ouvrir une session déjà existante en récupérant les signaux au format binaire présents dans le
 * dossier binaire de la session
 */
public class Session {
    /// ATTRIBUTS
    private final File directory;
    private final String[] listeFichiers;
    private final ArrayList<Saving> savings;

    /// CONSTRUCTOR

    /**
     * Création d'un objet Session qui regroupe tous les enregistrements à étudier ensemble
     * @param directory chemin d'accès à la session
     */
    public Session(File directory) {
        this.directory = directory;
        this.listeFichiers = directory.list();
        assert this.listeFichiers != null;
        this.savings = new ArrayList<>();
    }

    /// METHODS

    /**
     * Création d'une nouvelle session à partir des fichiers brut au format MTB-2 à la racine de la session
     */
    public void newSession() {
        for (String fichier : this.listeFichiers) {
            if (fichier.contains(".txt") || fichier.contains(".edf")) {
                Saving saving = new Saving(this.directory, fichier.replaceFirst("[" + ".][^.]+$", ""));
                saving.newSaving(fichier);
                this.savings.add(saving);
            }
        }
    }

    /**
     * Ouverture d'une session déjà existante en récupérant les signaux depuis les fichiers binaires existants
     */
    public void openSession() {
        for (String fichier : this.listeFichiers) {
            if (fichier.contains(".txt") || fichier.contains(".edf")) {
                Saving saving = new Saving(this.directory, fichier.replaceFirst("[" + ".][^.]+$", ""));
                saving.openSaving();
                this.savings.add(saving);
            }
        }
    }

    /**
     * Création d'une analyse de cycle des échantillons de tous les enregistrements
     * @param cycle cycle avec paramètres choisis par l'utilisateur
     * @param samples la liste complète de tous les échantillons
     */
    public void createCycleAnalysis(Cycle cycle, ArrayList<Sample> samples) {
        AnalysisFile analysisXlsFile = AnalysisFile.createSignalFile(directory, "cycle_statistics.xls", "xlsFile");
        ((XlsAnalysisFile) analysisXlsFile).writeCycleAnalysis(cycle, samples);
    }

    /**
     * Retourne la liste de tous les enregistrements de la session
     * @return savings
     */
    public ArrayList<Saving> getSavings() {
        return this.savings;
    }
}
