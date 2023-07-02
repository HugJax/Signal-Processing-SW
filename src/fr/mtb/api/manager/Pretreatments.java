package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

import java.util.ArrayList;

/**
 * Classe Pretreatments est une classe "liste qui regroupe tous les prétraitements à effectuer sur un signal avant
 * son analyse
 * Cette classe permet de créer cette liste de prétraitement et appelle chaque classe de prétraitement pour les
 * appliquer sur un signal donné
 */
public class Pretreatments {
    /// ATTRIBUTS
    private final ArrayList<Pretreatment> treatmentList;

    /// CONSTRUCTOR
    /**
     * Constructeur pour le traitement du signal brut
     * Mise en place d'une liste vide des prétraitements
     */
    public Pretreatments() {
        this.treatmentList = new ArrayList<>();
    }

    /// METHODS

    /**
     * Ajout d'un traitement de signal dans la liste
     * @param pretreatment prétraitement à effectuer
     */
    public void addTreatment(Pretreatment pretreatment) {
        this.treatmentList.add(pretreatment);
    }

    /**
     * Execute l'ensemble des prétraitements choisis par l'utilisateur et les appliques sur un signal donné
     * @param signal signal à traiter
     */
    public void execute(Signal signal) {
        for (Pretreatment pretreatment : this.treatmentList) {
            pretreatment.process(signal);
        }
    }
}
