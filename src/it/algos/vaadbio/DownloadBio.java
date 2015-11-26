package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaad.wiki.Page;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

import java.sql.Timestamp;

/**
 * Download della singola voce
 * <p>
 * Scarica la singola voce dal server e crea il nuovo record di Bio
 * Crea un nuovo record (solo se il pageid non esiste già)
 * Modifica il record esistente con lo stesso pageid
 * Registra il record Bio (solo pageid, title e templateServer)
 * Regola il flag temporale ultimaLettura
 * Azzera il flag temporale ultimaElaborazione
 * Regola il flag templateEsiste
 */
public class DownloadBio {

    private boolean status = false;

    private Bio bio;

    public DownloadBio(long pageId) {
        doInit(Api.leggePage(pageId));
    }// end of constructor


    public DownloadBio(String title) {
        doInit(Api.leggePage(title));
    }// end of constructor


    public DownloadBio(Page pagina) {
        doInit(pagina);
    }// end of constructor


    /**
     * Scarica la singola voce dal server e crea il nuovo record di Bio
     * Crea un nuovo record (solo se il pageid non esiste già)
     * Modifica il record esistente con lo stesso pageid
     * Registra il record Bio
     * Regola il flag temporale ultimaLettura
     * Azzera il flag temporale ultimaElaborazione
     * Regola il flag templateEsiste
     *
     * @param pagina dal server
     */
    private void doInit(Page pagina) {
        Bio bio = null;
        long pageid;
        String title;
        String testoVoce;
        String tmplBio;
        boolean templateEsiste = false;

        if (pagina == null) {
            return;
        }// fine del blocco if

        title = pagina.getTitle();
        pageid = pagina.getPageid();
        testoVoce = pagina.getText();
        tmplBio = Api.estraeTmplBio(testoVoce);

        if (tmplBio.equals("")) {
            if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
                Log.setWarn("bioCicloNew", "La pagina " + LibWiki.setQuadre(title) + ", è stata registrata ma manca il tmplBio");
            }// fine del blocco if
        } else {
            templateEsiste = true;
        }// end of if/else cycle

        try { // prova ad eseguire il codice
            bio = Bio.findByPageid(pageid);
        } catch (Exception unErrore) { // intercetta l'errore
            //--manca il DB
        }// fine del blocco try-catch

        if (bio == null) {
            try { // prova ad eseguire il codice
                bio = Bio.findByTitle(title);
            } catch (Exception unErrore) { // intercetta l'errore
                //--manca il DB
            }// fine del blocco try-catch
        }// fine del blocco if

        if (bio == null) {
            bio = new Bio();
        }// end of if cycle

        // regola altri parametri
        bio.setPageid(pageid);
        bio.setTitle(title);
        if (templateEsiste) {
            bio.setTemplateServer(tmplBio);
            bio.setTemplateEsiste(true);
        }// end of if cycle
        bio.setUltimaLettura(LibTime.adesso());
        bio.setUltimaElaborazione(new Timestamp(0));

        try { // prova ad eseguire il codice
            bio.save();
            setStatus(true);
        } catch (Exception unErrore) { // intercetta l'errore
            if (Pref.getBool(CostBio.USA_LOG_DOWNLOAD, true)) {
                Log.setWarn("bioCicloNew", "La pagina " + LibWiki.setQuadre(title) + ", non è stata registrata perché " + unErrore.getMessage());
            }// fine del blocco if
        }// fine del blocco try-catch

        this.setBio(bio);

        doElabora();
    }// end of method

    //--se è attivo il flag ed i template sono diversi, parte il ciclo di aggiornamento
    private void doElabora() {
        if (this.isStatus()) {
            bio = this.getBio();
            new ElaboraBio(bio, Pref.getBool(CostBio.USA_UPLOAD_DOWNLOADATA, false));
        }// fine del blocco if-else
    }// end of method

    public Bio getBio() {
        return bio;
    }// end of getter method

    private void setBio(Bio bio) {
        this.bio = bio;
    }//end of setter method

    public boolean isStatus() {
        return status;
    }// end of getter method

    private void setStatus(boolean status) {
        this.status = status;
    }//end of setter method


}// end of class
