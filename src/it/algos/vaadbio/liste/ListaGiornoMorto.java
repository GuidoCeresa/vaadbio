package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;

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
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    @Override
    protected ArrayList<Bio> getListaBio() {
        ArrayList<Bio> listaBio = null;
        Giorno giorno = super.getGiorno();

        if (giorno != null) {
            listaBio = giorno.bioMorti();
        }// end of if cycle

        return listaBio;
    }// fine del metodo

    /**
     * Chiave specifica della biografia (anno o giorno)
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        String key = CostBio.VUOTO;
        Anno anno = bio.getAnnoMortoPunta();

        if (anno != null) {
            key = anno.getTitolo();
        }// end of if cycle

        return key;
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
