package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.ParBio;

/**
 * Elabora valori validi dei parametri significativi
 */
public class ElaboraValidi {

    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraValidi(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    //--Elabora valori validi dei parametri significativi
    private void doInit(Bio bio) {

        if (bio != null) {
            for (ParBio par : ParBio.values()) {
                par.setBioValida(bio);
            } // fine del ciclo for-each
        }// fine del blocco if
    }// end of method


}// end of class
