package it.algos.vaadbio.upload;

import it.algos.vaadbio.liste.ListaNome;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.log.Log;
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
        ArrayList<Nome> listaNomi = Nome.findAll();
        long inizio = System.currentTimeMillis();

        for (Nome nome : listaNomi) {
            new ListaNome(nome);
        }// end of for cycle

        Log.setInfo("upload", "Aggiornate le pagine delle persone in " + LibTime.difText(inizio));
    }// end of method

}// fine della classe