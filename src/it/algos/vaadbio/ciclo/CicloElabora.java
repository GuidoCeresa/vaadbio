package it.algos.vaadbio.ciclo;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.elabora.Elabora;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Esegue un ciclo (ELABORA) di elaborazione dei records della tavola Bio
 * <p>
 * Il ciclo viene chiamato da DaemonElabora (con frequenza ?)
 * Il ciclo può essere invocato dal bottone 'Ciclo Elabora' nella tavola Bio
 * <p>
 * Controlla il flag USA_LIMITE_ELABORA
 * Usa il numero massimo (MAX_ELABORA) di voci da elaborare ad ogni ciclo (se USA_LIMITE_ELABORA=true)
 * Legge le voci Bio esistenti ordinate secondo il flag discendente ultimaElaborazione
 * Elabora ogni singolo record
 * Controlla il flag USA_UPLOAD_ELABORATA
 * Ricarica (se USA_UPLOAD_ELABORATA=true) ogni singolo record
 */
public class CicloElabora {

    /**
     * Costruttore completo
     */
    public CicloElabora() {
        doInit();
    }// end of constructor

    /**
     * Esegue un ciclo di elaborazione dei records della tavola Bio
     * <p>
     * Il ciclo viene chiamato da DaemonElabora (con frequenza ?)
     * Il ciclo può essere invocato dal bottone 'Ciclo Elabora' nella tavola Bio
     * <p>
     * Controlla il flag USA_LIMITE_ELABORA
     * Usa il numero massimo (MAX_ELABORA) di voci da elaborare ad ogni ciclo (se USA_LIMITE_ELABORA=true)
     * Legge le voci Bio esistenti ordinate secondo il flag ascendente ultimaElaborazione
     * Elabora ogni singolo record
     * Controlla il flag USA_UPLOAD_ELABORATA
     * Ricarica (se USA_UPLOAD_ELABORATA=true) ogni singolo record
     */
    public void doInit() {
        long inizio = System.currentTimeMillis();
        ArrayList<Long> vociDaElaborare;
        int limite = 0;
        String ultima = "";
        String message = "";
        int numRecordsElaborati = 0;
        int numVociUploadate = 0;
        boolean usaUpdate = Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false);
        Elabora elabora;

        // Recupera il numero globale di voci da elaborare; tutto il database oppure un tot stabilito nelle preferenze
        if (Pref.getBool(CostBio.USA_LIMITE_ELABORA, true)) {
            limite = Pref.getInt(CostBio.MAX_ELABORA, 1000);
            vociDaElaborare = Bio.findLast(limite);
        } else {
            vociDaElaborare = Bio.findLast();
        }// end of if/else cycle

        if (vociDaElaborare != null && vociDaElaborare.size() > 0) {
            for (Long pageid : vociDaElaborare) {
                elabora = new Elabora(pageid);
                if (elabora.isElaborata()) {
                    numRecordsElaborati++;
                }// end of if cycle

//                if (new ElaboraBio(pageid).isUploadata()) {
//                    numVociUploadate++;
//                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        //--Informazioni per il log
        if (numRecordsElaborati > 0) {
            ultima = Bio.findOldestElaborata();
            if (usaUpdate) {
                message += "Elaborati " + LibNum.format(numRecordsElaborati) + " records (di cui ";
                message += LibNum.format(numVociUploadate) + " ricaricati sul server) in " + LibTime.difText(inizio) + " " + ultima;
            } else {
                message += "Elaborati " + LibNum.format(numRecordsElaborati) + " records in " + LibTime.difText(inizio) + " " + ultima;
            }// end of if/else cycle

            Log.setInfo("elabora", message);
        }// end of if cycle

    }// end of method

}// end of class
