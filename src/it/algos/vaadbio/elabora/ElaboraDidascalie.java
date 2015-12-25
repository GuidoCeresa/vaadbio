package it.algos.vaadbio.elabora;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.didascalia.DidascaliaNatiGiorno;


/**
 * Created by gac on 23 dic 2015.
 * Elabora tutte le didascalie della voce
 */
public class ElaboraDidascalie {


    /**
     * Costruttore
     *
     * @param bio istanza da cui estrarre il tmplBioServer originale
     */
    public ElaboraDidascalie(Bio bio) {
        if (bio != null) {
            this.doInit(bio);
        }// end of if cycle
    }// end of constructor


    private void doInit(Bio bio) {
        String didascaliaGiornoNato = new DidascaliaNatiGiorno(bio).getTesto();
        bio.setDidascaliaGiornoNato(didascaliaGiornoNato);
    }// end of method

}// end of class
