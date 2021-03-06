package it.algos.vaadbio;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
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

    private Bio bio;

    private HashMap<String, Object> mappaReali;   //mappa di TUTTI i parametri esistenti nel tmplBioServer
    private HashMap<String, String> mappaBio;     //mappa dei parametri esistenti nella enumeration ParBio e presenti nel tmplBioServer

    private boolean elaborato = false;

    public ElaboraOnly(Bio bio) {
        this.bio = bio;
        doInit();
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
     * Regola il flag templateValido
     * Regola il flag templatesUguali
     */
    private void doInit() {
        String tmplBioServer = "";
        String templateStandard = "";
        boolean templateEsiste = false;

        if (bio == null) {
            return;
        }// end of if cycle

        if (checkVoce()) {
            bio.setTemplateValido(true);
        } else {
            bio.setTemplateValido(false);
        }// end of if/else cycle

        tmplBioServer = bio.getTmplBioServer();
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

        if (!templateStandard.equals("") && bio.getTmplBioServer().equals(templateStandard)) {
            bio.setTemplatesUguali(true);
        } else {
            bio.setTemplatesUguali(false);
        }// end of if/else cycle

        bio.setTemplateEsiste(templateEsiste);
        bio.setTmplBioStandard(templateStandard);
        bio.setUltimaElaborazione(LibTime.adesso());

        //--Elabora valori validi dei parametri significativi
        validaParametri(bio);

        //--Elabora i link alle tavole collegate
        elaboraLink(bio);

        try { // prova ad eseguire il codice
            bio.save();
            elaborato = true;
        } catch (Exception unErrore) { // intercetta l'errore
            //--Recupera i dati dal record della tavola Wikibio
            Log.debug("elabora", "Non sono riuscito ad elaborare la voce " + LibWiki.setQuadre(bio.getTitle()));
        }// fine del blocco try-catch

    }// end of method

    /**
     * Controlla la congruità della tmplBioServer (testo) prima di proseguire
     * <p>
     * Controlla l'esistenza del template bio
     * Controlla la presenza di note
     * Controlla la presenza di graffe
     * Controlla la presenza di testo nascosto
     */
    public boolean checkVoce() {
        boolean status = true;
        String tmplBioServer = "";
        String titoloVoce;
        String testoCompletoVoce;

        if (bio != null) {
            tmplBioServer = bio.getTmplBioServer();
        }// end of if cycle

        // controlla l'esistenza del template
        if (tmplBioServer.equals("")) {
            return false;
        }// fine del blocco if

        //--controlla che la pagina sia normale
        //--per cercare il template bio
        //--esclude redirect e disambigue
        if (false) {
        }// fine del blocco if

        //--controlla l'esistenza del template bio
        //--se manca, regola il flag
        if (!LibBio.isPariGraffe(tmplBioServer)) {
            return false;
        }// fine del blocco if

        // controlla la presenza di note
//        if (continua) {
//            if (WikiLib.hasNote(testoTemplate)) {
//                this.setNote(true)
//            }// fine del blocco if
//        }// fine del blocco if

        //--controlla la presenza di graffe
//        if (continua) {
//            if (WikiLib.hasGraffe(LibWiki.setNoGraffe(testoTemplate))) {
//                this.setGraffe(true)
//            }// fine del blocco if
//        }// fine del blocco if

        // controlla la presenza di testo nascosto
//        if (continua) {
//            if (WikiLib.hasNascosto(testoTemplate)) {
//                this.setNascosto(true)
//            }// fine del blocco if
//        }// fine del blocco if

        return status;
    } // fine del metodo


    /**
     * Regola i parametri della tavola in base alla mappa letta dal server
     * Aggiunge le date di riferimento lettura/scrittura
     */
    public void fixMappa(Bio bio, HashMap<String, String> mappa) {
        String key;
        Object value;

        for (ParBio par : ParBio.values()) {
            key = par.getTag();
            value = null;

//            if (mappa.get(key) != null) {
            value = mappa.get(key);
//            }// fine del blocco if

            par.setBio(bio, value);
        } // fine del ciclo for-each
        int a = 87;
    }// end of method

    /**
     * Costruisce il tmplBioStandard che serve per l'upload della singola voce sul server
     * <p>
     * Crea un nuovo template in base ai valori di questa istanza
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
     * Patch: se esiste categorie=no, eliminare attività e nazionalità
     */
    public String creaNewTmpl(Bio bio) {
        String text = "";

        if (bio != null && !bio.getTmplBioServer().equals("")) {
            text = "{{Bio\n";
            for (ParBio par : ParBio.values()) {
                text += par.getKeyValue(bio);
            } // fine del ciclo for-each
            text += "}}";
        }// end of if cycle

        return text;
    }// end of method


    //--Elabora valori validi dei parametri significativi
    private void validaParametri(Bio bio) {

        if (bio != null) {
            bio.setGiornoMeseNascitaValido(checkGiornoNato(bio));
            bio.setGiornoMeseMorteValido(checkGiornoMorto(bio));

//            bioGrails.annoNascitaLink = getAnnoNato(bioWiki)
//            bioGrails.giornoMeseMorteLink = getGiornoMorto(bioWiki)
//            bioGrails.annoMorteLink = getAnnoMorto(bioWiki)
//
//            bioGrails.attivitaLink = AttivitaService.getAttivita(bioGrails.attivita)
//            bioGrails.attivita2Link = AttivitaService.getAttivita(bioGrails.attivita2)
//            bioGrails.attivita3Link = AttivitaService.getAttivita(bioGrails.attivita3)
//            bioGrails.nazionalitaLink = NazionalitaService.getNazionalita(bioGrails.nazionalita)
//            bioGrails.luogoNatoLink = LocalitaService.getLuogoNascita(bioWiki)
//            bioGrails.luogoMortoLink = LocalitaService.getLuogoMorte(bioWiki)
//            bioGrails.nomeLink = antroponimoService.getAntroponimo(bioWiki.nome)
//            bioGrails.cognomeLink = cognomeService.getCognome(bioWiki.cognome)
//            bioGrails.save(flush: false)
        }// fine del blocco if
    }// end of method


    //--Elabora i link alle tavole collegate
    public void elaboraLink(Bio bio) {

        if (bio != null) {
            bio.setGiornoNatoPunta(getGiornoNato(bio));
            bio.setGiornoMortoPunta(getGiornoMorto(bio));

//            bioGrails.annoNascitaLink = getAnnoNato(bioWiki)
//            bioGrails.giornoMeseMorteLink = getGiornoMorto(bioWiki)
//            bioGrails.annoMorteLink = getAnnoMorto(bioWiki)
//
//            bioGrails.attivitaLink = AttivitaService.getAttivita(bioGrails.attivita)
//            bioGrails.attivita2Link = AttivitaService.getAttivita(bioGrails.attivita2)
//            bioGrails.attivita3Link = AttivitaService.getAttivita(bioGrails.attivita3)
//            bioGrails.nazionalitaLink = NazionalitaService.getNazionalita(bioGrails.nazionalita)
//            bioGrails.luogoNatoLink = LocalitaService.getLuogoNascita(bioWiki)
//            bioGrails.luogoMortoLink = LocalitaService.getLuogoMorte(bioWiki)
//            bioGrails.nomeLink = antroponimoService.getAntroponimo(bioWiki.nome)
//            bioGrails.cognomeLink = cognomeService.getCognome(bioWiki.cognome)
//            bioGrails.save(flush: false)
        }// fine del blocco if

    } // fine del metodo

    private String checkGiornoNato(Bio bio) {
        String giornoValido = "";
        String giornoGrezzo = "";

        if (bio != null) {
            giornoGrezzo = bio.getGiornoMeseNascita();

            if (!giornoGrezzo.contains(" ")) {
                return "";
            }// end of if cycle
            giornoValido = giornoGrezzo.trim();
        }// fine del blocco if

        return giornoValido;
    } // fine del metodo

    private String checkGiornoMorto(Bio bio) {
        String giornoValido = "";
        String giornoGrezzo = "";

        if (bio != null) {
            giornoGrezzo = bio.getGiornoMeseMorte();

            if (!giornoGrezzo.contains(" ")) {
                return "";
            }// end of if cycle
            giornoValido = giornoGrezzo.trim();
        }// fine del blocco if

        return giornoValido;
    } // fine del metodo


    private Giorno getGiornoNato(Bio bio) {
        Giorno giorno = null;
        String giornoWiki;

        if (bio != null) {
            giornoWiki = bio.getGiornoMeseNascitaValido();


            if (giornoWiki != null && !giornoWiki.equals("")) {
                giornoWiki = LibBio.fixCampoGiorno(giornoWiki);
                giornoWiki = Giorno.fix(giornoWiki);
                giorno = Giorno.findByTitolo(giornoWiki);
                if (giorno == null) {
                    giorno = Giorno.findByTitolo(giornoWiki);
                }// end of if cycle
            }// fine del blocco if
        }// fine del blocco if

        return giorno;
    } // fine del metodo

    private Giorno getGiornoMorto(Bio bio) {
        Giorno giorno = null;
        String giornoWiki;

        if (bio != null) {
            giornoWiki = bio.getGiornoMeseMorteValido();

            if (giornoWiki != null && !giornoWiki.equals("")) {
                giornoWiki = LibBio.fixCampoGiorno(giornoWiki);
                giornoWiki = Giorno.fix(giornoWiki);
                giorno = Giorno.findByTitolo(giornoWiki);
                if (giorno == null) {
                    giorno = Giorno.findByTitolo(giornoWiki);
                }// end of if cycle
            }// fine del blocco if
        }// fine del blocco if

        return giorno;
    } // fine del metodo


    public boolean isElaborato() {
        return elaborato;
    }// end of getter method

}// end of class
