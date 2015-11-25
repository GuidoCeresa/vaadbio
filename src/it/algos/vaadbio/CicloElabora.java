package it.algos.vaadbio;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;

/**
 * Esegue un ciclo di elaborazione dei records della tavola Bio
 * <p>
 * Il ciclo viene chiamato da DaemonElabora (con frequenza ?)
 * Il ciclo può essere invocato dal bottone 'Ciclo Elabora' nella tavola Bio
 * <p>
 * Controlla il flag USA_LIMITE_ELABORA
 * Usa il numero massimo (MAX_ELABORA) di voci da elaborare ad ogni ciclo (se USA_LIMITE_ELABORA=true)
 * Legge le voci Bio esistenti ordinate secondo il flag discendente ultimaElaborazione
 * Elabora ogni singolo record
 */
public class CicloElabora {

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
     */
    public void doInit() {
        ArrayList<Long> vociDaElaborare;
        int limite = 0;

        if (Pref.getBool(CostBio.USA_LIMITE_ELABORA, false)) {
            limite = Pref.getInt(CostBio.MAX_ELABORA, 100);
        }// fine del blocco if

        vociDaElaborare = Bio.findLast(limite);

        if (vociDaElaborare != null && vociDaElaborare.size() > 0) {
            for (Long pageid : vociDaElaborare) {
                new ElaboraBio(pageid);
            }// end of for cycle

        }// end of if cycle

    }// end of method

}// end of class
