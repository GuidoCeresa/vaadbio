package it.algos.vaadbio.upload;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaNome;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

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
        long inizio = System.currentTimeMillis();
        ArrayList<Nome> listaNomi = Nome.findAllSuperaTaglioPagina();
        int mod = 0;
        String modTxt;

        for (Nome nome : listaNomi) {
            if (new ListaNome(nome).isRegistrata()) {
                mod++;
            }// end of if cycle
        }// end of for cycle

        if (Pref.getBool(CostBio.USA_LOG_DEBUG)) {
            modTxt = LibNum.format(mod);
            if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA)) {
                Log.debug("upload", "Aggiornate tutte le pagine dei nomi in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate dei nomi (" + modTxt + ") in " + LibTime.difText(inizio));
            }// end of if/else cycle
        }// end of if cycle
    }// end of method

}// fine della classe
