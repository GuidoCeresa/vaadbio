package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 * Crea la lista dei morti nel giorno e la carica sul server wiki
 */
public abstract class ListaGiorno extends ListaBio {


    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiorno(Giorno giorno) {
        super(giorno);
    }// fine del costruttore


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String titolo = "";
        String tag = getTagTitolo();
        String articolo = "il";
        String articoloBis = "l'";
        Giorno giorno = getGiorno();

        if (giorno != null) {
            titolo = giorno.getTitolo();
        }// fine del blocco if

        if (!titolo.equals("")) {
            if (titolo.startsWith("8") || titolo.startsWith("11")) {
                titolo = tag + articoloBis + titolo;
            } else {
                titolo = tag + articolo + CostBio.SPAZIO + titolo;
            }// fine del blocco if-else
        }// fine del blocco if

        titoloPagina = titolo;
    }// fine del metodo


    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    protected String getTagTitolo() {
        return CostBio.VUOTO;
    }// fine del metodo


    public Giorno getGiorno() {
        return (Giorno)getOggetto();
    }// end of getter method

}// fine della classe
