package it.algos.vaadbio.elabora;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

/**
 * Elabora la singola voce
 * <p>
 * Invocato dalla classe Ciclo Elabora
 * Può essere invocato dal bottone 'Elabora' nel modulo Bio
 * <p>
 * Presuppone SEMPRE che esista già l'istanza bio scaricata (adesso o precedentemente) dal server Wiki
 * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
 * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
 * <p>
 * Elabora valori validi dei parametri significativi
 * <p>
 * Elabora i link alle tavole collegate
 * <p>
 * Registra l'istanza così modificata
 * <p>
 * L' upload della pagina NON viene fatto qui
 */
public class Elabora {

    private Bio bio = null;
    private boolean elaborata = false;


    /**
     * Costruttore
     *
     * @param pageid della voce da cui estrarre l'istanza bio (esistente)
     */
    public Elabora(long pageid) {
        Bio bio = null;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
        }// end of if cycle

        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore
     *
     * @param title della voce da cui estrarre l'istanza bio (esistente)
     */
    public Elabora(String title) {
        Bio bio = null;

        if (!title.equals("")) {
            bio = Bio.findByTitle(title);
        }// end of if cycle

        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore completo
     *
     * @param bio istanza da elaborare
     */
    public Elabora(Bio bio) {
        if (bio != null) {
            this.bio = bio;
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
     * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
     * <p>
     * Elabora valori validi dei parametri significativi
     * <p>
     * Elabora i link alle tavole collegate
     * <p>
     * Registra l'istanza così modificata
     */
    private void doInit() {
        //--Costruisce il tmplBioStandard che serve per l'upload della singola voce sul server
        creaTemplateStandard();

        //--Elabora valori validi dei parametri significativi
        validaParametri();

        //--Elabora i link alle tavole collegate
        elaboraLink();

        try { // prova ad eseguire il codice
            bio.setUltimaElaborazione(LibTime.adesso());
            bio.save();
            elaborata = true;
        } catch (Exception unErrore) { // intercetta l'errore
            //--Recupera i dati dal record della tavola Wikibio
            if (Pref.getBool(CostBio.USA_LOG_ELABORA, true)) {
                Log.setDebug("elabora", "Non sono riuscito ad elaborare la voce " + LibWiki.setQuadre(bio.getTitle()));
            }// end of if cycle

        }// fine del blocco try-catch
    }// end of method


    /**
     * Costruisce il tmplBioStandard che serve per l'upload della singola voce sul server
     * <p>
     * Crea un nuovo template in base ai valori della mappa
     * I parametri mancanti vengono presi dalla enumeration ParBio
     * Aggiunge il nome del template e le graffe iniziali e finali
     * <p>
     * Aggiunge i singoli parametri
     * - parametri in eccesso vengono scartati
     * - parametri errati vengono (se possibile secondo alcune regole) corretti
     * - parametri mancanti (obbligatori) vengono aggiunti con valori di default
     * <p>
     * I campi/parametri sono ordinati come l'enumeration ParBio
     * Riporta sempre i campi/parametri standard anche vuoti
     * Riporta gli altri campi/parametri solo se hanno un valore
     * <p>
     * b) prima riga = {{Bio
     * c) ultima riga = }} più \n
     * d) dopo il nome dei parametri spazio poi uguale poi spazio poi il valore
     * e) nessun commento aggiunto, esclusi gli eventuali parametri extra
     * f) tutti i parametri con valore, più quelli base
     * g) parametri base sempre presenti, anche se vuoti:
     * -    nome, cognome, sesso,
     * -    luogoNascita, luogoNascitaLink, giornoMeseNascita, annoNascita,
     * -    luogoMorte, luogoMorteLink, giornoMeseMorte, annoMorte,
     * -    attivita, attivita2, attivita3, nazionalita
     */
    private void creaTemplateStandard() {
        String tmplBioStandard = new ElaboraTemplate(bio).getTmplBioStandard();
        bio.setTmplBioStandard(tmplBioStandard);
    }// end of method


    /**
     * Elabora valori validi dei parametri significativi
     */
    private void validaParametri() {
        new ElaboraParametri(bio);
    }// end of method

    private void elaboraLink() {
    }// end of method

    public boolean isElaborata() {
        return elaborata;
    }// end of getter method

    public Bio getBio() {
        return bio;
    }// end of getter method
}// end of class
