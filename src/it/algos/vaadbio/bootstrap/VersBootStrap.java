package it.algos.vaadbio.bootstrap;

import it.algos.vaadbio.anno.AnnoService;
import it.algos.vaadbio.giorno.GiornoService;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.lib.LibPref;
import it.algos.webbase.web.lib.LibVers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
            if (GiornoService.creaGiorni()) {
                LibVers.nuova("Giorno", "Creati 366 records per tutti i giorni dell'anno (anche bisestile)");
            } else {
                LibVers.nuova("Giorno", "I giorni esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if

        //--crea i records della tavola Anno
        if (LibVers.installa(20)) {
            if (AnnoService.creaAnni()) {
                LibVers.nuova("Anno", "Creati 3030 records per gli anni dal 1000 a.c. al 2030 d.c.");
            } else {
                LibVers.nuova("Anno", "Gli anni esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if


        //--creata una nuova preferenza
        if (LibVers.installa(21)) {
            LibPref.newVersBool(CostBio.USA_TOC_INDICE_CRONO, false, "Uso dell'indice dei paragrafi in testa alle liste cronologiche. Tipicamente sempre falso.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(22)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_PORTALE_CRONO, true, "Uso del portale biografie nel footer delle liste cronologiche. Tipicamente sempre vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(23)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_CATEGORIE_CRONO, true, "Uso delle cateegorie nel footer delle liste cronologiche. Tipicamente sempre vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(24)) {
            LibPref.newVersBool(CostBio.USA_RITORNO_CRONO, true, "Uso del ritorno alla pagina madre in testa alle liste cronologiche. Tipicamente sempre vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(25)) {
            LibPref.newVersStr(CostBio.TEMPLATE_AVVISO_CRONO, "ListaBio", "Nome del template di avviso in testa alle liste cronologiche. Tipicamente ListaBio.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(26)) {
            LibPref.newVersBool(CostBio.USA_BODY_RIGHE_MULTIPLE_CRONO, true, "Uso delle righe multiple nelle liste cronologiche. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(27)) {
            LibPref.newVersBool(CostBio.REGISTRA_SOLO_MODIFICHE_SOSTANZIALI_CRONO, true, "Uso la registrazione solo se la voce è significativamente diversa nelle liste cronologiche. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(28)) {
            LibPref.newVersBool(CostBio.USA_BLOCCO_COMMIT, true, "Uso di un unico commit per un blocco di 500 records da registrare. Di default vero.");
        }// fine del blocco if

    }// end of method


    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method

}// end of bootstrap class
