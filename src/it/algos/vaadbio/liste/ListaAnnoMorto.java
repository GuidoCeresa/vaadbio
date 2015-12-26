package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 26 dic 2015.
 * <p>
 * Crea la lista dei morti nell'anno e la carica sul server wiki
 */
public class ListaAnnoMorto extends ListaAnno {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnnoMorto(Anno anno) {
        super(anno);
    }// fine del costruttore

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    @Override
    protected String getTagTitolo() {
        return "Morti ";
    }// fine del metodo


    /**
     * Costruisce la query specifica per la ricerca della lista biografica
     * Sovrascritto
     */
    @Override
    protected String getQueryCrono() {
        Anno anno = super.getAnno();
        String annoTxt = anno.getNome();
        String queryTxt = CostBio.VUOTO;

        queryTxt += "select bio.giornoMortoPunta.titolo,bio.didascaliaAnnoMorto from Bio bio where bio.annoMorte='";
        queryTxt += annoTxt;
        queryTxt += "' order by bio.giornoMortoPunta.bisestile,bio.cognome";

        return queryTxt;
    }// fine del metodo


}// fine della classe
