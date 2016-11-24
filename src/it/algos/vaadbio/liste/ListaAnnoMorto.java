package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;

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
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    protected ArrayList<Bio> getListaBio() {
        ArrayList<Bio> listaBio = null;
        Anno anno = super.getAnno();

        if (anno != null) {
            listaBio = anno.bioMorti();
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
        Giorno giorno = bio.getGiornoMortoPunta();

        if (giorno != null) {
            key = giorno.getTitolo();
        }// end of if cycle

        return key;
    }// fine del metodo


    /**
     * Didascalia specifica della biografia
     * Sovrascritto
     */
    @Override
    protected String getDidascalia(Bio bio) {
        return bio.getDidascaliaAnnoMorto();
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di morti per anno";
    }// fine del metodo

}// fine della classe
