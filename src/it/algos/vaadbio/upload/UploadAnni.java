package it.algos.vaadbio.upload;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.liste.ListaAnnoMorto;
import it.algos.vaadbio.liste.ListaAnnoNato;

import java.util.ArrayList;

/**
 * Esegue un ciclo di creazione (UPLOAD) delle liste di nati e morti per ogni anno
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera ?)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Anno
 * Il ciclo necessita del login come bot
 */
public class UploadAnni {


    /**
     * Costruttore completo
     */
    public UploadAnni() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di nati e morti per ogni giorno dell'anno
    private void doInit() {
        ArrayList<Anno> listaAnni= Anno.findAll();

        for (Anno anno : listaAnni) {
            new ListaAnnoNato(anno);
            new ListaAnnoMorto(anno);
        }// end of for cycle

    }// end of method

}// fine della classe
