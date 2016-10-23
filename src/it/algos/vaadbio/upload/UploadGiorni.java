package it.algos.vaadbio.upload;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaGiornoMorto;
import it.algos.vaadbio.liste.ListaGiornoNato;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

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
        long inizio = System.currentTimeMillis();
        int modNati = 0;
        int modMorti = 0;
        String modTxt;

        for (Giorno giorno : listaGiorni) {
            if (new ListaGiornoNato(giorno).isRegistrata()) {
                modNati++;
            }// end of if cycle
            if (new ListaGiornoMorto(giorno).isRegistrata()) {
                modMorti++;
            }// end of if cycle
        }// end of for cycle

        if (Pref.getBoolean(CostBio.USA_LOG_DEBUG, false)) {
            modTxt = LibNum.format(modNati) + "+" + LibNum.format(modMorti);
            if (Pref.getBoolean(CostBio.USA_REGISTRA_SEMPRE_CRONO, true)) {
                Log.debug("upload", "Aggiornate tutte (366*2) le pagine dei giorni (nati e morti) in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate (" + modTxt + ") dei giorni (nati e morti) in " + LibTime.difText(inizio));
            }// end of if/else cycle
        }// end of if cycle
    }// end of method

}// fine della classe
