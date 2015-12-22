package it.algos.vaadbio.upload;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.liste.ListaGiornoMorto;
import it.algos.vaadbio.liste.ListaGiornoNato;

import java.util.ArrayList;

/**
 * Esegue un ciclo di creazione (UPLOAD) delle liste di nati e morti per ogni giorno dell'anno
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera ?)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Giorno
 * Il ciclo necessita del login come bot
 */
public class UploadGiorni {


    /**
     * Costruttore completo
     */
    public UploadGiorni() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di nati e morti per ogni giorno dell'anno
    private void doInit() {
        ArrayList<Giorno> listaGiorni = Giorno.findAll();

        for (Giorno giorno : listaGiorni) {
            new ListaGiornoNato(giorno);
            new ListaGiornoMorto(giorno);
        }// end of for cycle

    }// end of method

}// fine della classe
