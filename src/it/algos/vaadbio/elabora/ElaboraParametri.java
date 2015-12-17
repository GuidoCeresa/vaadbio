package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;

/**
 * Elabora valori validi dei parametri significativi
 */
public class ElaboraParametri {

    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraParametri(Bio bio) {
        if (bio != null) {
            this.doInit();
        }// end of if cycle
    }// end of constructor

    /**
     * Elabora valori validi dei parametri significativi
     */
    private void doInit() {
    }// end of method


}// end of class
