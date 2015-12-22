package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;

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

}// fine della classe
