package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 26 dic 2015.
 * <p>
 * Crea la lista dei nati nell'anno e la carica sul server wiki
 */
public class ListaAnnoNato extends ListaAnno {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnnoNato(Anno anno) {
        super(anno);
    }// fine del costruttore

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    @Override
    protected String getTagTitolo() {
        return "Nati ";
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

        queryTxt += "select bio.didascaliaAnnoNato from Bio bio where bio.annoNascita='";
        queryTxt += annoTxt;
        queryTxt += "' order by bio.giornoNatoPunta.bisestile,bio.cognome";

        return queryTxt;
    }// fine del metodo


}// fine della classe
