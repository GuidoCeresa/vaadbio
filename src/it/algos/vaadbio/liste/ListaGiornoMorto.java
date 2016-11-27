package it.algos.vaadbio.liste;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.LibBio;

import java.util.HashMap;

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
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
        Giorno giorno = this.getGiorno();

        if (giorno != null) {
            listaBio = giorno.listaBioMorti();
        }// end of if cycle
    }// fine del metodo

    /**
     * Ordine progressivo del paragrafo (giorno oppure anno)
     * Sovrascritto
     */
    @Override
    protected int getOrdineCrono(HashMap<String, Object> mappa) {
        if (mappa != null && mappa.get(KEY_MAP_ORDINE_ANNO_MORTO) != null) {
            return (int) (mappa.get(KEY_MAP_ORDINE_ANNO_MORTO));
        } else {
            return 0;
        }// end of if/else cycle
    }// fine del metodo

    /**
     * Chiave specifica della biografia (anno o giorno)
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        return LibBio.getChiavePerAnnoMorto(bio, tagParagrafoNullo);
    }// fine del metodo


    /**
     * Didascalia specifica della biografia
     * Sovrascritto
     */
    @Override
    protected String getDidascalia(Bio bio) {
        return bio.getDidascaliaGiornoMorto();
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
