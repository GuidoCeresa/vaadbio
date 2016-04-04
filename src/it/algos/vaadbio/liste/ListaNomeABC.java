package it.algos.vaadbio.liste;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * /**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaNomeABC extends ListaNome {

    /**
     * Costruttore
     *
     * @param nome
     */
    public ListaNomeABC(Nome nome) {
        super(nome);
    }// fine del costruttore


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();
    }// fine del metodo

    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String tag = "Persone di nome ";

        titoloPagina = tag + getNomeTxt();
    }// fine del metodo

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
    }// fine del metodo

}// fine della classe
