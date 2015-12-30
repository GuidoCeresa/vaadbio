package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.entity.EM;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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

        queryTxt += "select bio.giornoMeseNascitaValido,bio.didascaliaAnnoNato from Bio bio where bio.annoNascitaValido='";
        queryTxt += annoTxt;
        queryTxt += "' order by bio.giornoMeseNascitaValido,bio.cognome";

        return queryTxt;
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di nati nell'anno";
    }// fine del metodo

}// fine della classe
