package fr.mtb.api.filesManager;

import fr.mtb.api.system.Signal;

import java.io.File;

/**
 * Classe SignalFile est la classe mère pour la lecture et l'écriture de signaux dans des fichiers de différents formats
 */
public class SignalFile {

    public enum SignalFileEnum {
        TXT("txtFile"), BIN("binFile"), EDF("edfFile");

        private final String signalFileType;

        SignalFileEnum(String signalFileType) {
            this.signalFileType = signalFileType;
        }

        public String toString() {
            return signalFileType;
        }
    }

    /// CONSTRUCTOR

    /**
     * Création de l'objet de gestion du fichier du signal
     * @param directory chemin d'accès de la session
     * @param filename nom de l'enregistrement
     * @param signalFileType type de fichier
     */
    public SignalFile(File directory, String filename, SignalFileEnum signalFileType) {

    }

    /// METHODS

    /**
     * Création d'un objet SignalFile correspondant au bon format de fichier
     * @param directory chemin d'accès
     * @param filename nom de l'enregistrement
     * @param type type de fichier
     * @return objet de gestion du fichier
     */
    public static SignalFile createSignalFile(File directory, String filename, String type) {
        SignalFile result = null;
        for (SignalFileEnum signalFileType : SignalFileEnum.values()) {
            if (signalFileType.toString().equals(type)) {
                switch (signalFileType) {
                    case TXT : {
                        result = new TxtSignalFile(directory, filename, signalFileType);
                        break;
                    }
                    case BIN : {
                        result = new BinSignalFile(directory, filename, signalFileType);
                        break;
                    }
                    case EDF : {
                        result = new EdfSignalFile(directory, filename, signalFileType);
                        break;
                    }
                    default :
                }
            }
        }
        return result;
    }

    public Signal read(Signal.SignalEnum signalEnum) {
        return null;
    }

    public void write(Signal signal, Signal.SignalEnum signalEnum) {

    }
}
