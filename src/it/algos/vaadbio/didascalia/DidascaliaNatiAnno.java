package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 25 dic 2015.
 * Didascalia specializzata per le liste di nati nell'anno.
 */
public class DidascaliaNatiAnno extends DidascaliaNati {


    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public DidascaliaNatiAnno(Bio bio) {
        super(bio);
    }// end of constructor


    /**
     * Costruisce il blocco iniziale (potrebbe non esserci)
     * Sovrascritto
     */
    @Override
    protected String getBloccoIniziale() {
        String text = CostBio.VUOTO;

        if (!giornoMeseNascita.equals(CostBio.VUOTO)) {
            text = LibWiki.setQuadre(giornoMeseNascita) + CostBio.TAG_SEPARATORE;
        }// fine del blocco if

        return text;
    }// end of method


    /**
     * Costruisce il testo della didascalia
     * Sovrascritto
     *
     * @Override
     */
    protected void regolaDidascalia() {
        if (!annoNascita.equals(CostBio.VUOTO)) {
            super.regolaDidascalia();
        }// fine del blocco if
    }// end of method

}// end of class