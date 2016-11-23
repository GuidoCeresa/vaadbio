package it.algos.vaadbio.upload;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.liste.ListaAntroCognome;
import it.algos.vaadbio.liste.ListaAttivita;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.List;

/**
 * Created by gac on 21 nov 2016.
 * Esegue un ciclo di creazione (UPLOAD) delle liste di attività
 * <p>
 * Il ciclo viene chiamato da DaemonCrono (con frequenza giornaliera ?)
 * Il ciclo può essere invocato dal bottone 'Upload all' nella tavola Attività
 * Il ciclo necessita del login come bot
 */
public class UploadAttivita {
    private int vociUplodate = 0;

    /**
     * Costruttore completo
     */
    public UploadAttivita() {
        doInit();
    }// end of constructor


    //--Esegue un ciclo di creazione (UPLOAD) delle liste di attività
    private void doInit() {
        long inizio = System.currentTimeMillis();
        List<Attivita> listaAttivitaDistinte= Attivita.findAllDistinct();
        int mod = 0;
        String modTxt;

        for (Attivita attivita : listaAttivitaDistinte) {
            if (new ListaAttivita(attivita).isRegistrata()) {
                mod++;
            }// end of if cycle
        }// end of for cycle

        if (Pref.getBool(CostBio.USA_LOG_DEBUG)) {
            modTxt = LibNum.format(mod);
            if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_ATT_NAZ)) {
                Log.debug("upload", "Aggiornate tutte le pagine di attività in " + LibTime.difText(inizio));
            } else {
                Log.debug("upload", "Aggiornate solo le pagine modificate delle attività (" + modTxt + ") in " + LibTime.difText(inizio));
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
