package fr.mtb.api.system;

import fr.mtb.api.filesManager.SignalFile;
import fr.mtb.api.manager.Analysis;
import fr.mtb.api.manager.Pretreatment;
import fr.mtb.api.manager.Pretreatments;

import java.io.File;

/**
 * Classe Saving correspond à un enregistrement
 * Il est possible de créer un nouvel enregistrement à partir d'un fichier en racine de la session ou à partir d'un
 * fichier binaire
 * Cette classe équivaut à un enregistrement brut et un enregistrement prétraité en fonction des prétraitements
 * choisis par l'utilisateur
 */
public class Saving {
    /// ATTRIBUTS
    private final String savingName;
    private final File directory;
    private Signal rawSignal;
    private Signal processedSignal;
    private final Pretreatments pretreatments;
    private Analysis analysis;

    /// CONSTRUCTOR

    /**
     * Création d'un objet enregistrement
     * @param directory chemin d'accès
     * @param savingName nom du fichier de l'enregistrement
     */
    public Saving(File directory, String savingName) {
        this.directory = directory;
        this.savingName = savingName.replaceFirst("[" + ".][^.]+$", "");
        this.pretreatments = new Pretreatments();
        this.analysis = new Analysis();
    }

    /// METHODS

    /**
     * Permet une analyse globale du signal
     */
    public void makeAnalysis() {
        this.analysis = this.analysis.makeAnalysis(this.processedSignal);
    }

    /**
     * Création d'un nouvel enregistrement à partir d'un fichier texte MTB-2 d'un signal brut à la racine de la session
     */
    public void newSaving(String savingName) {
        // lecture du fichier texte et récupération du signal brut
        if (savingName.contains(".txt")) {
            SignalFile txtSignalFile = SignalFile.createSignalFile(this.directory, this.savingName, "txtFile");
            this.rawSignal = txtSignalFile.read(Signal.SignalEnum.RAW);
        } else if (savingName.contains(".edf")) {
            SignalFile edfSignalFile = SignalFile.createSignalFile(this.directory, this.savingName, "edfFile");
            this.rawSignal = edfSignalFile.read(Signal.SignalEnum.RAW);
        }

        // création du fichier binaire du signal brut
        SignalFile binSignalFile = SignalFile.createSignalFile(this.directory, this.savingName, "binFile");
        binSignalFile.write(this.rawSignal, Signal.SignalEnum.RAW);
    }

    /**
     * Ouverture d'un enregistrement depuis un fichier binaire
     * Récupération du signal brut et du signal prétraité
     */
    public void openSaving() {
        // lecture du fichier binaire du signal brut
        SignalFile binSignalFile = SignalFile.createSignalFile(this.directory, this.savingName, "binFile");
        this.rawSignal = binSignalFile.read(Signal.SignalEnum.RAW);

        // lecture du fichier binaire du signal prétraité
        this.processedSignal = binSignalFile.read(Signal.SignalEnum.PROCESSED);
    }

    /**
     * Ajout d'un traitement à effectuer dans la liste des prétraitements
     * @param pretreatment prétraitement à effectuer
     */
    public void setPretreatment(Pretreatment pretreatment) {
        this.pretreatments.addTreatment(pretreatment);
    }

    /**
     * Création du signal à prétraité à partir du signal brut
     * Execution de l'ensemble des traitements choisis par l'utilisateur
     * Création du fichier binaire du signal prétraité
     */
    public void executePretreatments() {
        this.processedSignal = this.rawSignal.duplicate();

        this.pretreatments.execute(this.processedSignal);
        SignalFile binSignalFile = SignalFile.createSignalFile(this.directory, this.savingName, "binFile");
        binSignalFile.write(this.processedSignal, Signal.SignalEnum.PROCESSED);
    }

    public Signal getRawSignal() {
        return this.rawSignal;
    }

    public Signal getProcessedSignal() {
        return this.processedSignal;
    }

    public String getSavingName() {
        return this.savingName;
    }

    public Analysis getAnalysis() {
        return analysis;
    }
}
