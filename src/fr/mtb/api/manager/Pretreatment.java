package fr.mtb.api.manager;

import fr.mtb.api.system.Signal;

/**
 * Classe Pretreatment est une classe représente un prétraitement à effectuer sur un signal
 */
public class Pretreatment {
    /// ATTRIBUTS
    private PretreatmentEnum pretreatmentEnum;

    public enum PretreatmentEnum {
        RECTIFY("rectify"), SMOOTH("smooth"), DELETEARTEFACTS("deleteArtefacts"), SUBSAMPLING("subsampling");

        private final String PretreatmentType;

        PretreatmentEnum(String PretreatmentType) {
            this.PretreatmentType = PretreatmentType;
        }

        public String toString() {
            return PretreatmentType;
        }
    }

    /// CONSTRUCTOR

    /**
     * Création de l'objet de prétraitement
     * @param pretreatmentEnum enum spécifique au type de prétraitement que l'on souhaite effectuer
     */
    public Pretreatment(PretreatmentEnum pretreatmentEnum) {
        this.pretreatmentEnum = pretreatmentEnum;
    }

    /// METHODS

    /**
     * Création d'un prétraitement en fonction de l'enum donné par l'utilisateur
     * @param type type de prétraitement
     * @return le prétraitement à effectuer
     */
    public static Pretreatment createPretreatment(String type) {
        Pretreatment result = null;
        for (PretreatmentEnum pretreatmentType : PretreatmentEnum.values()) {
            if (pretreatmentType.toString().equals(type)) {
                switch (pretreatmentType) {
                    case RECTIFY: {
                        result = new Rectify(pretreatmentType);
                        break;
                    }
                    case SMOOTH: {
                        result = new Smooth(pretreatmentType);
                        break;
                    }
                    case DELETEARTEFACTS: {
                        result = new ReduceArtefacts(pretreatmentType);
                    }
                    case SUBSAMPLING: {
                        result = new SubSampling(pretreatmentType);
                    }
                    default :
                }
            }
        }
        return result;
    }

    public Signal process(Signal signal) {
        return null;
    }
}
