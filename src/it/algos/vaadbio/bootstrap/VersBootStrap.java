package it.algos.vaadbio.bootstrap;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibTimeBio;
import it.algos.webbase.web.lib.LibPref;
import it.algos.webbase.web.lib.LibVers;
import it.algos.webbase.web.lib.Secolo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Log delle versioni, modifiche e patch installate
 * Executed on container startup
 * Setup non-UI logic here
 * <p>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * È OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml
 */
public class VersBootStrap implements ServletContextListener {

    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * This method is called prior to the servlet context being
     * initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        //--prima installazione del programma
        //--non fa nulla, solo informativo
        if (LibVers.installa(1)) {
            LibVers.nuova("Setup", "Installazione iniziale");
        }// fine del blocco if

        // usi generali
        //--creata una nuova preferenza
        if (LibVers.installa(2)) {
            LibPref.newVersBool(CostBio.USA_DEBUG, false, "Flag generale di debug (ce ne possono essere di specifici, validi solo se questo è vero)");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(3)) {
            LibPref.newVersBool(CostBio.USA_DIALOGHI_CONFERMA, true, "Uso dei dialoghi di conferma prima dei cicli");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(4)) {
            LibPref.newVersBool(CostBio.USA_CICLI_ANCHE_SENZA_BOT, true, "Se non loggato, usa i cicli col limite mediawiki di 50 pagine");
        }// fine del blocco if


        // daemons
        //--creata una nuova preferenza
        if (LibVers.installa(5)) {
            LibPref.newVersBool(CostBio.USA_CRONO_DOWNLOAD, false, "Uso (tramite daemons in background) del cicloDownload per scaricare/aggiornare dal server i records Bio.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(6)) {
            LibPref.newVersBool(CostBio.USA_CRONO_ELABORA, false, "Uso (tramite daemons in background) del cicloElabora per sincronizzare i parametri esistenti dei records Bio");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(7)) {
            LibPref.newVersBool(CostBio.USA_LOG_DAEMONS, false, "Uso del log di registrazione per avvio e stop dei daemons", "Livello debug");
        }// fine del blocco if


        // ciclo download
        //--creata una nuova preferenza
        if (LibVers.installa(8)) {
            LibPref.newVersBool(CostBio.USA_LIMITE_DOWNLOAD, true, "Uso di un limite di pagine nel cicloDown per scaricare/aggiornare dal server i records Bio");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(9)) {
            LibPref.newVersInt(CostBio.MAX_DOWNLOAD, 50000, "Numero massimo di pagine da scaricare/aggiornare nel cicloDown");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(10)) {
            LibPref.newVersBool(CostBio.USA_LOG_DOWNLOAD, true, "Uso del log di registrazione nel cicloDown", "Livello debug");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(11)) {
            LibPref.newVersBool(CostBio.USA_UPLOAD_DOWNLOADATA, true, "Upload di ogni singola voce nel cicloDown. Down, Ela, se tmpl diverso: Up, Down, Ela");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(12)) {
            LibPref.newVersBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, true, "BioCicloUpdate, cancella una voce rimasta nel DB e non più presente sul server wiki");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(13)) {
            LibPref.newVersInt(CostBio.NUM_PAGEIDS_REQUEST, 500, "Numero di pageids nella request di controllo voci modificate nel cicloDown");
        }// fine del blocco if


        // ciclo elabora
        //--creata una nuova preferenza
        if (LibVers.installa(14)) {
            LibPref.newVersBool(CostBio.USA_LIMITE_ELABORA, true, "Uso di un limite di records nel cicloElabora per sincronizzare i parametri esistenti dei records Bio");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(15)) {
            LibPref.newVersInt(CostBio.MAX_ELABORA, 50000, "Numero massimo di records da elaborare nel cicloElabora");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(16)) {
            LibPref.newVersBool(CostBio.USA_LOG_ELABORA, true, "Uso del log di registrazione nel cicloElabora", "Livello debug");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(17)) {
            LibPref.newVersBool(CostBio.USA_UPLOAD_ELABORATA, true, "Upload di ogni singola voce nel cicloElabora. Ela, se tmpl diverso: Up, Down, Ela");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(18)) {
            LibPref.newVersBool(CostBio.USA_LOG_UPLOAD_ELABORATA, true, "Uso del log di registrazione per la singola voce uploadata", "Livello debug");
        }// fine del blocco if


        //--crea i records della tavola Giorno
        if (LibVers.installa(19)) {
            this.creaGiorni();
            LibVers.nuova("Giorno", "Creati 366 records per tutti i giorni dell'anno (anche bisestile)");
        }// fine del blocco if

        //--crea i records della tavola Anno
        if (LibVers.installa(20)) {
            this.creaAnni();
            LibVers.nuova("Anno", "Creati 3030 records per gli anni dal 1000 a.c. al 2030 d.c.");
        }// fine del blocco if

    }// end of method

    /**
     * Crea 366 records per tutti i giorni dell'anno
     * <p>
     * La colonna n, è il progressivo del giorno negli anni normali
     * La colonna b, è il progressivo del giorno negli anni bisestili
     */
    private void creaGiorni() {
        ArrayList<HashMap> listaAnno;
        String mese;
        String nome;
        String titolo;
        int normale;
        int bisestile;
        Giorno giorno;

        if (Giorno.count() > 0) {
            return;
        }// end of if cycle

        //--cancella tutti i records

        //costruisce i 366 records
        listaAnno = LibTimeBio.getAllGiorni();
        for (HashMap mappaGiorno : listaAnno) {
            mese = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO);
            nome = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME);
            titolo = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO);
            normale = (int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE);
            bisestile = (int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE);
            new Giorno(mese, nome, titolo, normale, bisestile).save();
        }// end of for cycle
    }// end of method


    /**
     * Crea tutti i records
     * Cancella prima quelli esistenti
     * <p>
     * Ante cristo dal 1000
     * Dopo cristo fino al 2030
     */
    private void creaAnni() {
        //--usato nell'ordinamento delle categorie
        final int ANNO_INIZIALE = 2000;
        int anteCristo = 1000;
        int dopoCristo = 2030;
        String tag = Secolo.TAG_AC;
        int progressivo;
        String titolo;
        String secolo;

        //--cancella tutti i records
//        Anno.executeUpdate('delete Anno')

        //costruisce gli anni prima di cristo dal 1000
        for (int k = anteCristo; k > 0; k--) {
            progressivo = ANNO_INIZIALE - k;
            titolo = k + tag;
            secolo = Secolo.getSecoloAC(k);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titolo, secolo, progressivo).save();
            }// end of if cycle
        }// end of for cycle

        //costruisce gli anni dopo cristo fino al 2030
        for (int k = 0; k <= dopoCristo; k++) {
            progressivo = k + ANNO_INIZIALE;
            titolo = k + "";
            secolo = Secolo.getSecoloDC(k);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titolo, secolo, progressivo).save();
            }// end of if cycle
        }// end of for cycle

    }// fine del metodo


    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method

}// end of bootstrap class
