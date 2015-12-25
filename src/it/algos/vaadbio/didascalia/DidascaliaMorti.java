package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 25 dic 2015.
 * Didascalia specializzata per le liste di morti nel giorno o nell'anno.
 * Sovrascritta nelle due sottoclassi concrete
 */
public abstract class DidascaliaMorti extends Didascalia {


    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public DidascaliaMorti(Bio bio) {
        super(bio);
    }// end of constructor


    /**
     * Costruisce il blocco finale (potrebbe non esserci)
     * Sovrascritto
     *
     * @return testo
     */
    @Override
    protected String getBloccoFinale() {
        String text = CostBio.VUOTO;
        String annoNascita = this.annoNascita;

        // costruisce il blocco finale (potrebbe non esserci)
        if (!annoNascita.equals(CostBio.VUOTO)) {
            text = CostBio.TAG_PARENTESI_INI + CostBio.TAG_NATO_CRONO;
            text += LibWiki.setQuadre(annoNascita);
            text += CostBio.TAG_PARENTESI_END;
        }// fine del blocco if

        return text;
    }// end of getter method

}// end of class
