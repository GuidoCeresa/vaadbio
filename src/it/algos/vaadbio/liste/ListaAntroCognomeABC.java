package it.algos.vaadbio.liste;

import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.nome.Nome;

import java.util.HashMap;

/**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaAntroCognomeABC extends ListaAntroCognome{

    /**
     * Costruttore
     *
     * @param cognome dell'antroponimo
     */
    public ListaAntroCognomeABC(Cognome cognome) {
        super(cognome);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperCognome della superPagina
     * @param mappa          del paragrafo
     */
    public ListaAntroCognomeABC(ListaAntroCognome listaSuperCognome, HashMap<String, Object> mappa) {
//        this.listaSuperNome = listaSuperNome;
//        this.oggetto = listaSuperNome.getOggetto();
//        this.paginaLinkata = (String) mappa.get(KEY_MAP_LINK);
//        this.titoloVisibile = (String) mappa.get(KEY_MAP_TITOLO);
//        this.sesso = (String) mappa.get(KEY_MAP_SESSO);
        doInit();
    }// fine del costruttore

}// fine della classe
