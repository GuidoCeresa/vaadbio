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

import javax.persistence.EntityManager;

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
    private Bio bio = null;

    /**
     * Costruttore
     *
     * @param pagina dal server
     */
    public WrapBio(Page pagina) {
        this(pagina, null);
    }// end of constructor


    /**
     * Costruttore
     *
     * @param pagina dal server
     */
    public WrapBio(Page pagina, EntityManager manager) {
        doInit(pagina, manager);
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
    private void doInit(Page pagina, EntityManager manager) {
        long pageid;
        String wikiTitle;
        String tmplBio;
        String testoPagina;

        if (pagina == null) {
            return;
        }// fine del blocco if

        if (pagina.getTitle() == null) {
            if (Pref.getBool(CostBio.USA_LOG_CICLO, true)) {
                Log.setDebug("cicloNew", "Una pagina (non so quale) non è valida");
            }// fine del blocco if
            return;
        }// fine del blocco if

        wikiTitle = pagina.getTitle();
        pageid = pagina.getPageid();
        tmplBio = Api.estraeTmplBio(pagina);

        if (tmplBio.equals("")) {
            registrata = false;
            if (Pref.getBool(CostBio.USA_LOG_CICLO, true)) {
                testoPagina = pagina.getText();
                if (testoPagina.startsWith(CostBio.DISAMBIGUA)) {
                    Log.setDebug("cicloNew", "La pagina " + LibWiki.setQuadre(wikiTitle) + ", non è stata registrata perché è una disambigua");
                    return;
                }// end of if cycle
                if (testoPagina.startsWith(CostBio.REDIRECT) || testoPagina.startsWith(CostBio.REDIRECT)) {
                    Log.setDebug("cicloNew", "La pagina " + LibWiki.setQuadre(wikiTitle) + ", non è stata registrata perché è un redirect");
                    return;
                }// end of if cycle
                Log.setDebug("cicloNew", "La pagina " + LibWiki.setQuadre(wikiTitle) + ", non è stata registrata perché  manca il tmplBio");
                return;
            }// fine del blocco if
            return;
        }// end of if/else cycle

        try { // prova ad eseguire il codice
            bio = Bio.findByPageid(pageid);
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        if (bio == null) {
            long inizio2 = System.currentTimeMillis();
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
        bio.setTmplBioServer(tmplBio);
        bio.setTemplateEsiste(true);
        bio.setUltimaLettura(LibTime.adesso());

        //--Elabora l'istanza
        registrata = new Elabora(bio, manager).isElaborata();
    }// end of method

    public boolean isRegistrata() {
        return registrata;
    }// end of getter method

    public Bio getBio() {
        return bio;
    }// end of getter method

}// end of class
