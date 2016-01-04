package it.algos.vaadbio.upload;

import it.algos.vaadbio.anno.Anno;
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
     * Costruttore completo normale
     */
    public UploadAnni() {
        this(true);
    }// end of constructor

    /**
     * Costruttore completo contrario
     */
    public UploadAnni(boolean daPrimaDiCristo) {
        doInit(daPrimaDiCristo);
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di nati e morti per ogni giorno dell'anno
    private void doInit(boolean daPrimaDiCristo) {
        ArrayList<Anno> listaAnni = Anno.findAll();
        Anno annoContrario;

        if (daPrimaDiCristo) {
            for (Anno annoNormale : listaAnni) {
                new ListaAnnoNato(annoNormale);
                new ListaAnnoMorto(annoNormale);
            }// end of for cycle
        } else {
            for (int k = 3029; k > 0; k--) {
                annoContrario =listaAnni.get(k);
                new ListaAnnoNato(annoContrario);
                new ListaAnnoMorto(annoContrario);
            }// end of for cycle
        }// end of if/else cycle

    }// end of method

}// fine della classe
