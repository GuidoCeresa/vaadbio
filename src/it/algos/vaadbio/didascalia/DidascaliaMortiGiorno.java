package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 25 dic 2015.
 * Didascalia specializzata per le liste di morti nel giorno.
 */
public class DidascaliaMortiGiorno extends DidascaliaMorti {


    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public DidascaliaMortiGiorno(Bio bio) {
        super(bio);
    }// end of constructor


    /**
     * Costruisce il blocco iniziale (potrebbe non esserci)
     * Sovrascritto
     */
    @Override
    protected String getBloccoIniziale() {
        String text = CostBio.VUOTO;

        if (!annoMorte.equals(CostBio.VUOTO)) {
            text = LibWiki.setQuadre(annoMorte) + CostBio.TAG_SEPARATORE;
        }// fine del blocco if

        return text;
    }// end of method

}// end of class