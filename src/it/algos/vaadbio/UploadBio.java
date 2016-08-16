package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

/**
 * Scrive sul server wiki
 * Necessita del Login come Bot
 */
public class UploadBio {

    private boolean scritta = false;

    public UploadBio(long pageId) {
        super();
        doInit(pageId);
    }// end of constructor

    public UploadBio(String wikiTitle) {
        super();
        doInit(wikiTitle);
    }// end of constructor


    public UploadBio(Bio bio) {
        super();
        doInit(bio);
    }// end of constructor

    /**
     * Recupera l'istanza di Bio
     *
     * @param pageid della voce da elaborare
     */
    private void doInit(long pageid) {
        Bio bio;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
            if (bio != null) {
                doInit(bio);
            }// end of if cycle
        }// end of if cycle

    }// end of method

    /**
     * Recupera l'istanza di Bio
     *
     * @param wikiTitle della voce da elaborare
     */
    private void doInit(String wikiTitle) {
        Bio bio;

        if (!wikiTitle.equals("")) {
            bio = Bio.findByTitle(wikiTitle);
            if (bio != null) {
                doInit(bio);
            }// end of if cycle
        }// end of if cycle

    }// end of method


    /**
     * Scrive sul server wiki
     * <p>
     * Usa i valori dell'istanza Bio
     * Utilizza il template tmplBioStandard
     * Legge la pagina
     * Sostituisce il template esistente (sul server) con tmplBioStandard
     * Scrive la pagina
     */
    private void doInit(Bio bio) {
        String wikiTitle = "";
        String oldTextVoce;
        String oldTempl;
        String newTempl;
        String newTextVoce;
        String summary = LibWiki.getSummary("fix par temp");

        if (bio != null && !bio.isTemplatesUguali()) {
            wikiTitle = bio.getTitle();
            oldTextVoce = Api.leggeVoce(wikiTitle);
            oldTempl = LibWiki.estraeTmplBioCompresi(oldTextVoce);
            newTempl = bio.getTmplBioStandard();
            newTextVoce = LibText.sostituisce(oldTextVoce, oldTempl, newTempl);

            scritta = Api.scriveVoce(wikiTitle, newTextVoce, summary);
        }// end of if cycle

        if (scritta && Pref.getBoolean(CostBio.USA_LOG_UPLOAD_ELABORATA, true)) {
            Log.debug("upload", "Caricata sul server la voce: " + LibWiki.setQuadre(wikiTitle));
        }// fine del blocco if

    }// end of method

    public boolean isScritta() {
        return scritta;
    }// end of getter method

}// end of class
