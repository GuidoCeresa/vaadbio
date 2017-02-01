package it.algos.vaadbio.upload;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAntroCognome;
import it.algos.vaadbio.liste.ListaAttivita;
import it.algos.vaadbio.liste.ListaNazionalita;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.List;

/**
 * Created by gac on 31/01/17.
 * Esegue un ciclo di creazione (UPLOAD) delle liste di nazionalità
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Nazionalità
 * Il ciclo necessita del login come bot
 */
public class UploadNazionalita {
    private int vociUplodate = 0;

    /**
     * Costruttore completo
     */
    public UploadNazionalita() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di nazionalità
    private void doInit() {
        long inizio = System.currentTimeMillis();
        List<Nazionalita> listaNazionalitaDistinte = Nazionalita.findAllDistinct();
        int mod = 0;
        String modTxt;

        for (Nazionalita nazionalita : listaNazionalitaDistinte) {
            if (new ListaNazionalita(nazionalita).isRegistrata()) {
                mod++;
            }// end of if cycle
        }// end of for cycle

        if (Pref.getBool(CostBio.USA_LOG_DEBUG)) {
            modTxt = LibNum.format(mod);
            if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_ATT_NAZ)) {
                Log.debug("upload", "Aggiornate tutte le pagine di nazionalità in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate delle nazionalità (" + modTxt + ") in " + LibTime.difText(inizio));
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
