package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 */
public class ListaGiornoNato extends ListaGiorno {

    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiornoNato(Giorno giorno) {
        super(giorno);
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
        String queryTxt = CostBio.VUOTO;
        Giorno giorno = super.getGiorno();
        String giornoTxt = giorno.getTitolo();

        queryTxt += "select bio.annoNascitaValido,bio.didascaliaGiornoNato from Bio bio where bio.giornoMeseNascitaValido='";
        queryTxt += giornoTxt;
        queryTxt += "' order by bio.annoNascitaValido,bio.cognome";

        return queryTxt;
    }// fine del metodo


    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di nati per giorno";
    }// fine del metodo



}// fine della classe
