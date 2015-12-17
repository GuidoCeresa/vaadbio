package it.algos.vaadbio.wrapperbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.elabora.Elabora;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

/**
 * Ciclo di gestione della voce DOPO che la pagina è stata scaricata dal server wiki
 * <p>
 * Controlla che la pagina sia valida
 * Estrae dalla pagina i parametri pageid, title e tmplBioServer
 * Recupera l'istanza bio tramite il pageid, oppure tramite il title
 * Crea un nuovo record (solo se non esiste già)
 * Regola l'istanza solo per i parametri pageid e title
 * Regola il flag temporale ultimaLettura
 * Elabora (tramite classe dedicata) l'istanza e registra le modifiche
 */
public class WrapBio {

    private boolean registrata = false;

    /**
     * Costruttore
     *
     * @param pagina dal server
     */
    public WrapBio(Page pagina) {
        doInit(pagina);
    }// end of constructor


    /**
     * Controlla che la pagina sia valida
     * Estrae dalla pagina i parametri pageid, title e tmplBioServer
     * Recupera l'istanza bio tramite il pageid, oppure tramite il title
     * Crea un nuovo record (solo se non esiste già)
     * Regola l'istanza solo per i parametri pageid e title
     * Regola il flag temporale ultimaLettura
     * Elabora (tramite classe dedicata) l'istanza e registra le modifiche
     *
     * @param pagina dal server
     */
    private void doInit(Page pagina) {
        Bio bio = null;
        long pageid;
        String wikiTitle;
        String tmplBio;
        boolean templateEsiste = false;

        if (pagina == null) {
            return;
        }// fine del blocco if

        if (pagina.getTitle() == null) {
            if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
                Log.setDebug("cicloNew", "Una pagina (non so quale) non è valida");
            }// fine del blocco if
            return;
        }// fine del blocco if

        wikiTitle = pagina.getTitle();
        pageid = pagina.getPageid();
        tmplBio = Api.estraeTmplBio(pagina);

        if (tmplBio.equals("")) {
            if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
                Log.setDebug("cicloNew", "La pagina " + LibWiki.setQuadre(wikiTitle) + ", non è stata registrata perché  manca il tmplBio");
            }// fine del blocco if
        } else {
            templateEsiste = true;
        }// end of if/else cycle

        try { // prova ad eseguire il codice
            bio = Bio.findByPageid(pageid);
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        if (bio == null) {
            try { // prova ad eseguire il codice
                bio = Bio.findByTitle(wikiTitle);
            } catch (Exception unErrore) { // intercetta l'errore
            }// fine del blocco try-catch
        }// end of if cycle

        if (bio == null) {
            bio = new Bio();
        }// end of if cycle

        // regola parametri non gestiti in Elabora
        bio.setPageid(pageid);
        bio.setTitle(wikiTitle);
        if (templateEsiste) {
            bio.setTmplBioServer(tmplBio);
            bio.setTemplateEsiste(true);
        }// end of if cycle
        bio.setUltimaLettura(LibTime.adesso());

        //--Elabora l'istanza
        registrata = new Elabora(bio).isElaborata();
    }// end of method

    public boolean isRegistrata() {
        return registrata;
    }// end of getter method

}// end of class
