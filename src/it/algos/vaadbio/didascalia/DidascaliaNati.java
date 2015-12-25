package it.algos.vaadbio.didascalia;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 25 dic 2015.
 * Didascalia specializzata per le liste di nati nel giorno o nell'anno.
 * Sovrascritta nelle due sottoclassi concrete
 */
public abstract class DidascaliaNati extends Didascalia {


    /**
     * Costruttore
     *
     * @param bio istanza da cui costruire la didascalia specifica
     */
    public DidascaliaNati(Bio bio) {
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
        String annoMorte = this.annoMorte;

        // costruisce il blocco finale (potrebbe non esserci)
        if (!annoMorte.equals(CostBio.VUOTO)) {
            text = CostBio.TAG_PARENTESI_INI + CostBio.TAG_MORTO_CRONO;
            text += LibWiki.setQuadre(annoMorte);
            text += CostBio.TAG_PARENTESI_END;
        }// fine del blocco if

        return text;
    }// end of getter method

}// end of class
