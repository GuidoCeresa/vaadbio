package it.algos.vaadbio;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.lib.ParBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.web.lib.LibTime;

import java.util.HashMap;

/**
 * Elabora la singola pagina
 * <p>
 * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
 * Modifica alcuni parametri rispetto ai parametri originali con lo stesso nome, secondo logiche specifiche per ogni parametro
 * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
 */
public class ElaboraOnly {

    private HashMap<String, Object> mappaReali;   //mappa di TUTTI i parametri esistenti nel tmplBioServer
    private HashMap<String, Object> mappaBio;     //mappa dei parametri esistenti nella enumeration ParBio e presenti nel tmplBioServer

    public ElaboraOnly(Bio bio) {
        doInit(bio);
    }// end of constructor

    /**
     * Estrae dal tmplBioServer i singoli parametri previsti nella enumeration ParBio
     * Costruisce alcuni parametri mopdificando i parametri originali con lo stesso nome, secondo logiche specifiche per ogni parametro
     * Costruisce il tmplBioStandard usando i parametri validi (ove esistenti) ed i parametri originali. Elimina i parametri vuoti.
     * <p>
     * Controlla la consistenza del tmplBioServer (manca, incompleto, errato, eccessivo, corretto)
     * Crea/aggiorna il record Bio estraendo dal tmplBioServer i singoli parametri
     * - parametri in eccesso vengono scartati
     * - parametri errati vengono (se possibile secondo alcune regole) corretti
     * - parametri mancanti (obbligatori) vengono aggiunti con valori di default
     * Costruisce il tmplBioStandard che serve come base valida per l'upload della singola voce sul server
     * Elabora i link alle tavole collegate
     * Crea le didascalie
     * Regola il flag temporale ultimaElaborazione
     *
     * @param bio da elaborare
     */
    private void doInit(Bio bio) {
        String tmplBioServer = "";
        String templateStandard = "";
        boolean templateEsiste = false;

        if (bio == null) {
            return;
        }// end of if cycle

        tmplBioServer = bio.getTemplateServer();
        if (tmplBioServer != null && !tmplBioServer.equals("")) {
            mappaReali = LibBio.getMappaReali(tmplBioServer);
            mappaBio = LibBio.getMappaBio(tmplBioServer);
            templateEsiste = true;
        }// end of if cycle

        if (templateEsiste) {
            if (mappaBio != null) {
                fixMappa(bio, mappaBio);
            }// fine del blocco if
            templateStandard = creaNewTmpl(bio);
        }// end of if cycle

        if (!templateStandard.equals("") && bio.getTemplateServer().equals(templateStandard)) {
            bio.setTemplatesUguali(true);
        } else {
            bio.setTemplatesUguali(false);
        }// end of if/else cycle

        bio.setTemplateEsiste(templateEsiste);
        bio.setTemplateStandard(templateStandard);
        bio.setUltimaElaborazione(LibTime.adesso());

        try { // prova ad eseguire il codice
            bio.save();
        } catch (Exception unErrore) { // intercetta l'errore
            //--Recupera i dati dal record della tavola Wikibio
            Log.setDebug("elabora", "Non sono riuscito ad elaborare la voce " + LibWiki.setQuadre(bio.getTitle()));
        }// fine del blocco try-catch

    }// end of method


    /**
     * Regola i parametri della tavola in base alla mappa letta dal server
     * Aggiunge le date di riferimento lettura/scrittura
     */
    public void fixMappa(Bio bio, HashMap<String, Object> mappa) {
        String key;
        Object value;

        for (ParBio par : ParBio.values()) {
            key = par.getTag();
            value = null;

            if (mappa.get(key) != null) {
                value = mappa.get(key);
            }// fine del blocco if

            par.setBio(bio, value);
        } // fine del ciclo for-each
    }// end of method

    /**
     * Costruisce il tmplBioStandard che serve come base valida per l'upload della singola voce sul server
     * <p>
     * Crea un nuovo template in base ai valori di questa istanza
     * Aggiunge il nome del template e le graffe iniziali e finali
     * <p>
     * Aggiunge i singoli parametri
     * - parametri in eccesso vengono scartati
     * - parametri errati vengono (se possibile secondo alcune regole) corretti
     * - parametri mancanti (obbligatori) vengono aggiunti con valori di default
     */
    public String creaNewTmpl(Bio bio) {
        String text = "";

        if (bio != null && !bio.getTemplateServer().equals("")) {
            text = "{{Bio\n";
            for (ParBio par : ParBio.values()) {
                text += par.getKeyValue(bio);
            } // fine del ciclo for-each
            text += "}}";
        }// end of if cycle

        return text;
    }// end of method

}// end of class
