package it.algos.vaadbio;

import it.algos.vaadbio.bio.Bio;

/**
 * Elabora la singola pagina
 * Controlla la equivalenza di tmplBioServer con tmplBioStandard
 * Eventuale Upload della pagina
 * Eventuale Download della pagina
 * Regola il flag temporale ultimaElaborazione
 */
public class ElaboraBio {


    public ElaboraBio(long pageid) {
        doInit(pageid);
    }// end of constructor

    public ElaboraBio(Bio bio) {
        doInit(bio);
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
     * Elabora la singola pagina
     * <p>
     * Controlla la equivalenza di tmplBioServer con tmplBioStandard
     * Eventuale Upload della pagina
     * Eventuale Download della pagina
     * Regola il flag temporale ultimaElaborazione
     *
     * @param bio da elaborare
     */
    private void doInit(Bio bio) {
        new ElaboraOnly(bio);
    }// end of method

}// end of class
