package fr.mtb.api.manager;

import fr.mtb.api.filesManager.AnalysisFile;
import fr.mtb.api.filesManager.SignalFile;
import fr.mtb.api.system.*;
import fr.mtb.api.util.Date;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println(new Date().toStringCompleteDateFormat());
        double slidingAverage = 0.2;

        File directory = new File("./sessions/Souris/");
        Session session = new Session(directory);

        Cycle cycle = new Cycle(new Date(1, 6, 2022, 9, 49, 40), new Time(12, 0, 0), new Time(0, 30, 0));
        ArrayList<Sample> samples = new ArrayList<>();

        session.newSession();
        //session.openSession();

        for (Saving saving : session.getSavings()) {
            // création des prétraitements
            Pretreatment deleteArtefacts = Pretreatment.createPretreatment("deleteArtefacts");
            Pretreatment rectify = Pretreatment.createPretreatment("rectify");
            Pretreatment smooth = Pretreatment.createPretreatment("smooth");
            ((Smooth)smooth).defineSlidingAverage(slidingAverage);

            // ajout des prétraitements dans la liste
            saving.setPretreatment(deleteArtefacts);
            saving.setPretreatment(rectify);
            saving.setPretreatment(smooth);
            saving.executePretreatments();

            // gestion des cycles
            //samples.addAll(cycle.createSamples(saving.getProcessedSignal()));

            // écriture du fichier prétraité
            SignalFile txtSignalFile = SignalFile.createSignalFile(directory, saving.getSavingName(), "txtFile");
            txtSignalFile.write(saving.getProcessedSignal(), Signal.SignalEnum.PROCESSED);

            // écriture du fichier d'analyse par enregistrement
            saving.makeAnalysis();
            AnalysisFile analysisTxtFile = AnalysisFile.createSignalFile(directory, saving.getSavingName(), "txtFile");
            analysisTxtFile.write(saving.getAnalysis());
            AnalysisFile analysisBinFile = AnalysisFile.createSignalFile(directory, saving.getSavingName(), "binFile");
            analysisBinFile.write(saving.getAnalysis());
        }

        // écriture de l'analyse globale des cycles
        //session.createCycleAnalysis(cycle, samples);
        System.out.println(new Date().toStringCompleteDateFormat());
    }
}
