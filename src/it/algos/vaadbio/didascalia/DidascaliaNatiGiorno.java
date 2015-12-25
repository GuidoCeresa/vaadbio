package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 23 dic 2015.
 * .
 */
public class DidascaliaNatiGiorno extends Didascalia {


    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public DidascaliaNatiGiorno(Bio bio) {
        super(bio);
    }// end of constructor

    /**
     * Costruisce il blocco iniziale (potrebbe non esserci)
     * Sovrascritto
     */
    protected String getBloccoIniziale() {
        String text = CostBio.VUOTO;
        String tagSep = " - ";

        if (!annoNascita.equals(CostBio.VUOTO)) {
            text = LibWiki.setQuadre(annoNascita) + tagSep;
        }// fine del blocco if

        return text;
    }// end of method

}// end of class
