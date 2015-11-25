package it.algos.vaadbio;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Elabora la singola pagina
 * Controlla la equivalenza di tmplBioServer con tmplBioStandard
 * Eventuale Upload della pagina
 * Eventuale Download della pagina
 * Regola il flag temporale ultimaElaborazione
 */
public class ElaboraBio {

    private boolean upload;

    public ElaboraBio(long pageid) {
        this(pageid, Pref.getBool(CostBio.USA_UPLOAD_ELABORATA, false));
    }// end of constructor

    public ElaboraBio(long pageid, boolean upload) {
        doInit(pageid, upload);
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
     * Eventuale Upload della pagina
     * Eventuale Download della pagina
     * Regola il flag temporale ultimaElaborazione
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
     * Eventuale Upload della pagina
     * Eventuale Download della pagina
     * Regola il flag temporale ultimaElaborazione
     *
     * @param bio da elaborare
     */
    private void doInit(Bio bio, boolean upload) {
        this.upload = upload;
        new ElaboraOnly(bio);
    }// end of method

}// end of class
