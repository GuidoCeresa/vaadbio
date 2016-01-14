package it.algos.vaadbio.statistiche;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.elabora.Elabora;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Created by gac on 14 gen 2016.
 * Crea la tabella ed aggiorna la pagina Progetto:Biografie/Statistiche
 * <p>
 * La pagina di sintesi viene chiamato da DaemonUpload (con frequenza ?)
 * La pagina di sintesi può essere invocata dal bottone 'Sintesi' nella tavola Bio
 * <p>
 * Legge la terza colonna della tabella esistente
 * Recupera i dati per costruire la terza colonna
 * Elabora i dati per costruire la quarta colonna
 */
public class Sintesi extends Statistiche{

    public static String PATH = "Progetto:Biografie/Statistiche";

    /**
     * Costruttore completo
     */
    public Sintesi() {
        super();
        doInit();
    }// end of constructor

    /**
     * Crea la tabella ed aggiorna la pagina Progetto:Biografie/Statistiche
     * <p>
     * La pagina di sintesi viene chiamato da DaemonUpload (con frequenza ?)
     * La pagina di sintesi può essere invocata dal bottone 'Sintesi' nella tavola Bio
     * <p>
     * Legge la terza colonna della tabella esistente
     * Recupera i dati per costruire la terza colonna
     * Elabora i dati per costruire la quarta colonna
     */
    public void doInit() {

    }// end of method


//    def paginaSintesi() {
//        String titolo = PATH + 'Statistiche'
//        String testo = ''
//        String summary = Pref.getStr(LibBio.SUMMARY)
//        def nonServe
//        boolean debug = Pref.getBool(LibBio.DEBUG, true)
//        testo += getTestoTop()
//        testo += getTestoBodySintesi()
//        testo += getTestoBottomSintesi()
//
//        if (debug) {
//            titolo = 'Utente:Biobot/2'
//        }// fine del blocco if
//
//        if (titolo && testo && summary) {
//            nonServe = new Edit(titolo, testo, summary)
//            if (!debug) {
//                registraPreferenze()
//            }// fine del blocco if
//        }// fine del blocco if
//    }// fine del metodo




}// end of class
