package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei morti nel giorno e la carica sul server wiki
 */
public class ListaGiornoMorto extends ListaGiorno {

    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiornoMorto(Giorno giorno) {
        super(giorno);
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
        String queryTxt = CostBio.VUOTO;
        Giorno giorno = super.getGiorno();
        String giornoTxt = giorno.getTitolo();

        queryTxt += "select bio.annoMortoPunta.nome,bio.didascaliaGiornoMorto from Bio bio where bio.giornoMeseMorte='";
        queryTxt += giornoTxt;
        queryTxt += "' order by bio.annoMortoPunta.ordinamento,bio.cognome";

        return queryTxt;
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di morti per giorno";
    }// fine del metodo


}// fine della classe
