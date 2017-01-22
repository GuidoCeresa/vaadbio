package it.algos.vaadbio.bootstrap;

import it.algos.vaadbio.anno.AnnoService;
import it.algos.vaadbio.attivita.AttivitaService;
import it.algos.vaadbio.genere.GenereService;
import it.algos.vaadbio.giorno.GiornoService;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.mese.MeseService;
import it.algos.vaadbio.nazionalita.NazionalitaService;
import it.algos.vaadbio.professione.ProfessioneService;
import it.algos.vaadbio.secolo.SecoloService;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.domain.pref.PrefType;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibPref;
import it.algos.webbase.web.lib.LibTime;
import it.algos.webbase.web.lib.LibVers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

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
        int k = 0;

        //--prima installazione del programma
        //--non fa nulla, solo informativo
        if (LibVers.installa(++k)) {
            LibVers.nuova("Setup", "Installazione iniziale");
        }// fine del blocco if

        //--crea i records della tavola Mese (propedeutica per Giorno)
        if (LibVers.installa(++k)) {
            if (MeseService.creaMesi()) {
                LibVers.nuova("Mese", "Creati 12 records per i mesi dell'anno");
            } else {
                LibVers.nuova("Mese", "I mesi esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if


        //--crea i records della tavola Giorno
        if (LibVers.installa(++k)) {
            if (GiornoService.creaGiorni()) {
                LibVers.nuova("Giorno", "Creati 367 records per tutti i giorni dell'anno (anche bisestile)");
            } else {
                LibVers.nuova("Giorno", "I giorni esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if


        //--crea i records della tavola Secolo (propedeutica per Anno)
        if (LibVers.installa(++k)) {
            if (SecoloService.creaSecoli()) {
                LibVers.nuova("Secolo", "Creati 41 records dei secoli prima e dopo Cristo");
            } else {
                LibVers.nuova("Secolo", "I secoli esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if


        //--crea i records della tavola Anno
        if (LibVers.installa(++k)) {
            if (AnnoService.creaAnni()) {
                LibVers.nuova("Anno", "Creati 3030 records per gli anni dal 1000 a.c. al 2030 d.c.");
            } else {
                LibVers.nuova("Anno", "Gli anni esistevano già e non sono stati modificati");
            }// end of if/else cycle
        }// fine del blocco if


        //--creazione iniziale dei records della tavola Attività
        if (LibVers.installa(++k)) {
            if (AttivitaService.download()) {
                LibVers.nuova("Attività", "Creazione iniziale dei records letti dalla pagina wiki");
            } else {
                LibVers.nuova("Attività", "Non sono riuscito a leggere la pagina wiki");
            }// end of if/else cycle
        }// fine del blocco if

        //--creazione iniziale dei records della tavola Nazionalità
        if (LibVers.installa(++k)) {
            if (NazionalitaService.download()) {
                LibVers.nuova("Nazionalità", "Creazione iniziale dei records letti dalla pagina wiki");
            } else {
                LibVers.nuova("Nazionalità", "Non sono riuscito a leggere la pagina wiki");
            }// end of if/else cycle
        }// fine del blocco if

        //--creazione iniziale dei records della tavola Genere
        if (LibVers.installa(++k)) {
            if (GenereService.download()) {
                LibVers.nuova("Genere", "Creazione iniziale dei records letti dalla pagina wiki");
            } else {
                LibVers.nuova("Genere", "Non sono riuscito a leggere la pagina wiki");
            }// end of if/else cycle
        }// fine del blocco if

        //--creazione iniziale dei records della tavola Professione
        if (LibVers.installa(++k)) {
            if (ProfessioneService.download()) {
                LibVers.nuova("Professione", "Creazione iniziale dei records letti dalla pagina wiki");
            } else {
                LibVers.nuova("Professione", "Non sono riuscito a leggere la pagina wiki");
            }// end of if/else cycle
        }// fine del blocco if

        // usi generali
        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DEBUG, false, "Flag generale di debug (ce ne possono essere di specifici, validi solo se questo è vero)");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_LOG_DAEMONS, false, "Uso del log di registrazione per avvio e stop dei daemons", "Livello debug");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DIALOGHI_CONFERMA, true, "Uso dei dialoghi di conferma prima di lanciare le operazioni");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_CICLI_ANCHE_SENZA_BOT, true, "Uso dei cicli col limite mediawiki di 50 pagine, se non loggato");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.NUM_PAGEIDS_REQUEST, 500, "Numero di pageids nella request di controllo in cicloUpdate. Tipicamente sempre 500");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_LOG_CICLO, true, "Uso del log di registrazione nel cicloDownload e nel cicloElabora", "Livello debug");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_LIMITE_CICLO, false, "Uso di un limite di pagine nel cicloDownload e nel cicloElabora");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.MAX_CICLO, 100000, "Numero max di pagine da scaricare/aggiornare nel cicloDownload e nel cicloElabora");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_DOWNLOAD, true, "Uso (in background) di cicloDownload, cicloUpdate, cicloElabora");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_CRONO, true, "Uso (in background) di uploadGiorni ed uploadAnni");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_ATTIVITA, false, "Uso (in background) di uploadAttivita");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_NAZIONALITA, false, "Uso (in background) di uploadNazionalita");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_NOMI, false, "Uso (in background) di uploadNomi");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_COGNOMI, false, "Uso (in background) di uploadCognomi");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_LOCALITA, false, "Uso (in background) di uploadLocalita");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_DAEMONS_LOCALITA, false, "Uso (in background) di uploadGiorni e uploadAnni");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersData(CostBio.STAT_DATA_ULTIMA_SINTESI, LibTime.adesso(), "Data di riferimento per l'aggiornamento delle statistiche.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_NUM_VOCI, 298000, "Numero di voci gestite. Di default poco meno della categoria.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_NUM_GIORNI, 367, "Numero di giorni gestiti nell'anno (bisestile compreso). Di default 367.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_NUM_ANNI, 3016, "Numero di anni gestiti (1.000 a.C. + 2016 d.C.). Di default 3016.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_NUM_ATTIVITA, 600, "Numero di attività gestite . Di default circa 600.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_NUM_NAZIONALITA, 300, "Numero di nazionalità gestite . Di default circa 300.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.STAT_GIORNI_ATTESA, 5, "Numero di giorni di attesa prima di aggiornare le statistiche. Di default circa 5.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.NUM_VOCI_ANNI, 300, "Soglia per le statistiche degli anni. Di default 300");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_COMMIT_MULTI_RECORDS, false, "Uso di un commit multiplo per registrare un blocco di records. Di default falso.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.NUM_RECORDS_COMMIT, 500, "Numero di records per ogni commit. Di default 500.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_NOME_SINGOLO, true, "Uso del nome singolo: solo il primo. Di default true.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_LOG_DEBUG, true, "Uso del log di registrazione per il livello debug. Di default true.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.TAGLIO_NOMI_PAGINA, 50, "Numero di voci necessario per creare la pagina del nome");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.TAGLIO_NOMI_ELENCO, 20, "Numero di voci necessario per elencare il nome nella lista");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.MAX_VOCI_PARAGRAFO, 100, "Numero di voci del paragrafo per creare una sotto-pagina");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_REGISTRA_SEMPRE_CRONO, false, "Registra sempre la pagina di giorni ed anni. Di default falso.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA, false, "Registra sempre la pagina di nomi e cognomi. Di default falso.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_REGISTRA_SEMPRE_ATT_NAZ, false, "Registra sempre la pagina di attività e nazionalità. Di default falso.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_CASE_UGUALI, true, "Nei titoli dei paragrafi, maiuscole e minuscole uguali. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_PORTALE_CRONO, true, "Portale biografie nel footer delle liste crono. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_CATEGORIE_CRONO, true, "Categorie nel footer delle liste crono. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_PORTALE_NOMI, true, "Portale antroponimi nel footer delle liste di persone. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_FOOTER_CATEGORIE_NOMI, true, "Categorie nel footer delle liste di persone. Di default vero.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_HEAD_NON_SCRIVERE, true, "Avvertenza di 'non scrivere' nelle liste. Di default vero.");
        }// fine del blocco if

        //--converte preferenze
        if (LibVers.installa(++k)) {
            new ConvertePreferenze();
            LibVers.nuova("Preferenze", "Convertite alla nuova versione");
        }// fine del blocco if

        //--fix errore del tipo di una preferenza
        if (LibVers.installa(++k)) {
            Pref pref = Pref.findByCode(CostBio.STAT_DATA_ULTIMA_SINTESI);
            if (pref != null) {
                pref.setTipo(PrefType.date);
                pref.setValore(new Date());
                pref.save();
            }// end of if cycle
            LibVers.nuova("Preferenze", "Corretto il tipo della preferenza 'ultimaSintesi'");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.TAGLIO_COGNOMI_ELENCO, 20, "Taglio per registrare il record e creare la riga nella lista statistica. Di default 20.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersInt(CostBio.TAGLIO_COGNOMI_PAGINA, 50, "Taglio per creare una pagina del cognome. Di default 50.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_RICONTEGGIO_NOMI, false, "Ricalcola il numero di voci per ogni nome in creazione della tabella. Di default false.");
        }// fine del blocco if

        //--elimina una property come riferimento
        //-- ricordarsi di eseguire manualmente -update WIKI.NOME set riferimento_id=null-
        if (LibVers.installa(++k)) {
            fixPropertyBio();
            LibVers.nuova("Bio", "La property Bio.nomePunta (deprecata) viene resa nulla, per poter cancellare la tavola Nomi");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_NUMERI_PARAGRAFO, false, "Aggiunge (in piccolo) il numero delle voci contenute nel paragrafo. Di default false.");
        }// fine del blocco if

        //--creata una nuova preferenza
        if (LibVers.installa(++k)) {
            LibPref.newVersBool(CostBio.USA_NOMI_DIVERSI_PER_ACCENTO, true, "Nomi diversi per accenti diversi: Mária # Maria. Di default true.");
        }// fine del blocco if


        //--elimina una property come riferimento
//        if (LibVers.installa(++k)) {
//            fixPropertyNome();
//            LibVers.nuova("Nome", "La property Nome.riferimento (deprecata) viene resa nulla, per poter cancellare la tavola Nomi");
//        }// fine del blocco if

//        //--creata una nuova preferenza
//        if (LibVers.installa(11)) {
//            LibPref.newVersBool(CostBio.USA_UPLOAD_DOWNLOADATA, false, "Upload di ogni singola voce nel cicloDownload. Down, Ela, se tmpl diverso: Up, Down, Ela");
//        }// fine del blocco if
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(12)) {
//            LibPref.newVersBool(CostBio.USA_CANCELLA_VOCE_MANCANTE, true, "CicloDownload, cancella una voce rimasta nel DB e non più presente sul server wiki");
//        }// fine del blocco if
//
//
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(17)) {
//            LibPref.newVersBool(CostBio.USA_UPLOAD_ELABORATA, true, "Upload di ogni singola voce nel cicloElabora. Ela, se tmpl diverso: Up, Down, Ela");
//        }// fine del blocco if
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(18)) {
//            LibPref.newVersBool(CostBio.USA_LOG_UPLOAD_ELABORATA, false, "Uso del log di registrazione per la singola voce uploadata", "Livello debug");
//        }// fine del blocco if
//
//


//        //--creata una nuova preferenza
//        if (LibVers.installa(21)) {
//            LibPref.newVersBool(CostBio.USA_TOC_INDICE_CRONO, false, "Uso dell'indice dei paragrafi in testa alle liste cronologiche. Tipicamente sempre falso.");
//        }// fine del blocco if
//
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(24)) {
//            LibPref.newVersBool(CostBio.USA_RITORNO_CRONO, true, "Uso del ritorno alla pagina madre in testa alle liste cronologiche. Tipicamente sempre vero.");
//        }// fine del blocco if
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(25)) {
//            LibPref.newVersStr(CostBio.TEMPLATE_AVVISO_CRONO, "ListaBio", "Nome del template di avviso in testa alle liste cronologiche. Tipicamente ListaBio.");
//        }// fine del blocco if
//
//        //--creata una nuova preferenza
//        if (LibVers.installa(26)) {
//            LibPref.newVersBool(CostBio.USA_BODY_RIGHE_MULTIPLE_CRONO, true, "Uso delle righe multiple nelle liste cronologiche. Di default vero.");
//        }// fine del blocco if
//
//
    }// end of method


    /**
     * Rende nullo il valore della property di Bio che linka alla tavola Nome
     * Per poter poi cancellare la tavola Nome, evitando l'integrità referenziale
     */
    public void fixPropertyBio() {
        EntityManager manager = EM.createEntityManager();
        Query query;
        String queryTxt = "update Bio bio set bio.nomePunta=null";
        try { // prova ad eseguire il codice
            manager.getTransaction().begin();
            query = manager.createQuery(queryTxt);
            query.executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception unErrore) { // intercetta l'errore
            manager.getTransaction().rollback();
        }// fine del blocco try-catch
    }// end of method

    /**
     * Rende nullo il valore della property di Nome che linka ad un altro record della stessa tavola Nome
     * Per poter poi cancellare la tavola Nome, evitando l'integrità referenziale
     */
    public void fixPropertyNome() {
        EntityManager manager = EM.createEntityManager();
        Query query;
        String queryTxt = "update Nome nome set nome.riferimento_id=null";
        try { // prova ad eseguire il codice
            manager.getTransaction().begin();
            query = manager.createQuery(queryTxt);
            query.executeUpdate();
            manager.getTransaction().commit();
        } catch (Exception unErrore) { // intercetta l'errore
            manager.getTransaction().rollback();
        }// fine del blocco try-catch
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
