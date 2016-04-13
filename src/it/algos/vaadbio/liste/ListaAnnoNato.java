package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    @Override
    protected ArrayList<Bio> getListaBio() {
        ArrayList<Bio> listaBio = null;
        Anno anno = super.getAnno();

        if (anno != null) {
            listaBio = anno.bioNati();
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
        Giorno giorno = bio.getGiornoNatoPunta();

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
        return bio.getDidascaliaAnnoNato();
    }// fine del metodo


    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di nati per anno";
    }// fine del metodo

}// fine della classe
