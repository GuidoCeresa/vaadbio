package it.algos.vaadbio.upload;

import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAntroCognome;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Created by gac on 30 ott 2016.
 * Esegue un ciclo di creazione (UPLOAD) delle liste di cognomi
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera ?)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Cognomi
 * Il ciclo necessita del login come bot
 */
public class UploadCognomi {

    private int vociUplodate=0;

    /**
     * Costruttore completo
     */
    public UploadCognomi() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di cognomi
    private void doInit() {
        long inizio = System.currentTimeMillis();
        ArrayList<Cognome> listaCognomi = Cognome.findAllSuperaTaglioPagina();
        int mod = 0;
        String modTxt;

        for (Cognome cognome : listaCognomi) {
            if (new ListaAntroCognome(cognome).isRegistrata()) {
                mod++;
            }// end of if cycle
        }// end of for cycle

        if (Pref.getBool(CostBio.USA_LOG_DEBUG)) {
            modTxt = LibNum.format(mod);
            if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA)) {
                Log.debug("upload", "Aggiornate tutte le pagine dei cognomi in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate dei cognomi (" + modTxt + ") in " + LibTime.difText(inizio));
            }// end of if/else cycle
        }// end of if cycle
    }// end of method

    public int getVociUplodate() {
        return vociUplodate;
    }// end of getter method

    public void setVociUplodate(int vociUplodate) {
        this.vociUplodate = vociUplodate;
    }//end of setter method
}// fine della classe
