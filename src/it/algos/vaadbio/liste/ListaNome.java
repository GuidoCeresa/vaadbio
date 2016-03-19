package it.algos.vaadbio.liste;

import it.algos.vaadbio.nome.Nome;

/**
 * Created by gac on 27 dic 2015.
 * Crea la lista delle persone col nome indicato e la carica sul server wiki
 */
public class ListaNome extends ListaBio {


    /**
     * Costruttore
     *
     * @param nome
     */
    public ListaNome(Nome nome) {
        super(nome);
    }// fine del costruttore

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
        super.elaboraMappaBiografie();
    }// fine del metodo
}// fine della classe
