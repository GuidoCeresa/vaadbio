package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.nome.Nome;

import java.util.HashMap;

/**
 * Created by gac on 27 dic 2015.
 * Crea la lista delle persone col nome indicato e la carica sul server wiki
 */
public class ListaAntroNome extends ListaAntroponimo {


    /**
     * Costruttore senza parametri
     */
    protected ListaAntroNome() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param nome indicato
     */
    public ListaAntroNome(Nome nome) {
        super(nome);
    }// fine del costruttore


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
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    @Override
    protected String elaboraIncipitSpecifico() {
        String text = "incipit lista nomi|nome=" + getNomeTxt();
        return LibWiki.setGraffe(text);
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
        Nome nome = this.getNome();

        if (nome != null) {
            listaBio = nome.listaBio();
        }// end of if cycle
    }// fine del metodo


    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaAntroNomeABC(this, mappa);
    }// fine del metodo


    /**
     * Categorie al piede della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraCategorie() {
        return "[[Categoria:Liste di persone per nome|" + getNomeTxt() + "]]";
    }// fine del metodo


    public Nome getNome() {
        return (Nome) getOggetto();
    }// end of getter method


    public String getNomeTxt() {
        String nomeTxt = "";
        Nome nome = getNome();

        if (nome != null) {
            nomeTxt = nome.getNome();
        }// end of if cycle

        return nomeTxt;
    }// end of getter method

}// fine della classe
