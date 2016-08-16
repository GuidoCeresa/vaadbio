package it.algos.vaadbio.upload;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAnnoMorto;
import it.algos.vaadbio.liste.ListaAnnoNato;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

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
        long inizio = System.currentTimeMillis();
        int modNati = 0;
        int modMorti = 0;
        String modTxt;

        if (daPrimaDiCristo) {
            for (Anno annoNormale : listaAnni) {
                if (new ListaAnnoNato(annoNormale).isRegistrata()) {
                    modNati++;
                }// end of if cycle
                if (new ListaAnnoMorto(annoNormale).isRegistrata()) {
                    modMorti++;
                }// end of if cycle
            }// end of for cycle
        } else {
            for (int k = 3029; k > 0; k--) {
                annoContrario = listaAnni.get(k);
                if (new ListaAnnoNato(annoContrario).isRegistrata()) {
                    modNati++;
                }// end of if cycle
                if (new ListaAnnoMorto(annoContrario).isRegistrata()) {
                    modMorti++;
                }// end of if cycle
            }// end of for cycle
        }// end of if/else cycle

        if (Pref.getBoolean(CostBio.USA_LOG_DEBUG)) {
            modTxt = LibNum.format(modNati) + "+" + LibNum.format(modMorti);
            if (Pref.getBoolean(CostBio.USA_REGISTRA_SEMPRE_CRONO)) {
                Log.debug("upload", "Aggiornate tutte (3020*2) le pagine degli anni (nati e morti) in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate (" + modTxt + ") degli anni (nati e morti) in " + LibTime.difText(inizio));
            }// end of if/else cycle
        }// end of if cycle
    }// end of method

}// fine della classe
