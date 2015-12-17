package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.lib.ParBio;

import java.util.HashMap;

/**
 * Elabora i contenuti di un template Bio
 * Presuppone SEMPRE che esista già l'istanza bio scaricata (adesso o precedentemente) dal server Wiki
 * <p>
 * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
 * Crea un templateStandard con i parametri
 */
public class ElaboraTemplate {

    private String tmplBioServer = "";
    private String tmplBioStandard = "";
    private HashMap<String, String> mappaBio;   //mappa dei parametri della enumeration ParBio

    private boolean valido = false;

    /**
     * Costruttore
     *
     * @param pageid della voce da cui estrarre l'istanza bio (esistente) da cui estrarre il tmplBioServer originale
     */
    public ElaboraTemplate(long pageid) {
        Bio bio = null;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
        }// end of if cycle

        if (bio != null) {
            this.tmplBioServer = bio.getTmplBioServer();
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraTemplate(Bio bio) {
        if (bio != null) {
            this.tmplBioServer = bio.getTmplBioServer();
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Costruttore completo
     *
     * @param tmplBioServer originale proveniente dal download del server
     */
    public ElaboraTemplate(String tmplBioServer) {
        this.tmplBioServer = tmplBioServer;
        this.doInit();
    }// end of constructor

    /**
     * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
     * Crea un templateStandard con i parametri
     */
    private void doInit() {
        estraeMappa();
        creaTemplateStandard();
    }// end of method

    /**
     * Estrae dal templateServer una mappa di parametri corrispondenti ai campi della tavola Bio
     */
    private void estraeMappa() {
        mappaBio = LibBio.getMappaBio(tmplBioServer);
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
     * <p>
     * Patch: se esiste categorie=no, eliminare attività e nazionalità //@todo dubbi
     */
    private void creaTemplateStandard() {
        String text = "";

        if (mappaBio == null || mappaBio.size() < 1) {
            return;
        }// end of if cycle

        text = "{{Bio\n";
        for (ParBio par : ParBio.values()) {
            if (mappaBio.get(par.getTag()) != null) {
                text += par.getKeyValue(mappaBio.get(par.getTag()));
            } else {
                text += par.getKeyValue("");
            }// end of if/else cycle
        } // fine del ciclo for-each
        text += "}}";

        tmplBioStandard = text;
        if (!tmplBioStandard.equals("")) {
            valido = true;
        }// end of if cycle

    }// end of method


    public String getTmplBioStandard() {
        return tmplBioStandard;
    }// end of getter method

    public HashMap<String, String> getMappaBio() {
        return mappaBio;
    }// end of getter method

    public boolean isValido() {
        return valido;
    }// end of getter method

}// end of class
