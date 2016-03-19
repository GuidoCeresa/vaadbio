package it.algos.vaadbio.upload;

import it.algos.vaadbio.liste.ListaNome;
import it.algos.vaadbio.nome.Nome;

import java.util.ArrayList;

/**
 * Esegue un ciclo di creazione (UPLOAD) delle liste di nomi
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera ?)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Nomi
 * Il ciclo necessita del login come bot
 */
public class UploadNomi {


    /**
     * Costruttore completo
     */
    public UploadNomi() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di nomi
    private void doInit() {
        ArrayList<Nome> listaNomi = Nome.findAll();

        for (Nome nome : listaNomi) {
            new ListaNome(nome);
        }// end of for cycle
    }// end of method

}// fine della classe
