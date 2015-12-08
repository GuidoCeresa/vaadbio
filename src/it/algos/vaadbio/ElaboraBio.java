package it.algos.vaadbio;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Elabora la singola pagina
 * <p>
 * Controlla la equivalenza di tmplBioServer con tmplBioStandard
 * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
 */
public class ElaboraBio {

    private boolean uploadata = false;

    public ElaboraBio(long pageid) {
        this(pageid, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(long pageid, boolean upload) {
        doInit(pageid, upload);
    }// end of constructor

    public ElaboraBio(String wikiTitle) {
        this(wikiTitle, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(String wikiTitle, boolean upload) {
        doInit(wikiTitle, upload);
    }// end of constructor

    public ElaboraBio(Bio bio) {
        this(bio, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(Bio bio, boolean upload) {
        doInit(bio, upload);
    }// end of constructor

    /**
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
     *
     * @param pageid della voce da elaborare
     */
    private void doInit(long pageid, boolean upload) {
        Bio bio;

        if (pageid > 0) {
            bio = Bio.findByPageid(pageid);
            if (bio != null) {
                doInit(bio, upload);
            }// end of if cycle
        }// end of if cycle

    }// end of method

    /**
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
     *
     * @param wikiTitle della voce da elaborare
     */
    private void doInit(String wikiTitle, boolean upload) {
        Bio bio;

        if (!wikiTitle.equals("")) {
            bio = Bio.findByTitle(wikiTitle);
            if (bio != null) {
                doInit(bio, upload);
            }// end of if cycle
        }// end of if cycle

    }// end of method

    /**
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina, seguito da un ulteriore Download della pagina
     *
     * @param bio da elaborare
     */
    private void doInit(Bio bio, boolean upload) {
        Bio bioDopoDownload;
        long pageid;
        boolean elaborato = new ElaboraOnly(bio).isElaborato();

        if (bio == null) {
            return;
        }// end of if cycle
        pageid = bio.getPageid();

        if (upload && elaborato) {
            if (!bio.isTemplatesUguali()) {
                new UploadBio(bio);
                bioDopoDownload = new DownloadBio(pageid, false).getBio();
                new ElaboraOnly(bioDopoDownload);
            }// end of if cycle
        }// end of if cycle

    }// end of method

    public boolean isUploadata() {
        return uploadata;
    }// end of getter method
}// end of class
