package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.cognome.Cognome;

import java.util.HashMap;

/**
 * Created by gac on 16 mag 2016.
 * Crea la lista delle persone col cognome indicato e la carica sul server wiki
 */
public class ListaAntroCognome extends ListaAntroponimo {


    /**
     * Costruttore senza parametri
     */
    public ListaAntroCognome() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param cognome indicato
     */
    public ListaAntroCognome(Cognome cognome) {
        super(cognome);
    }// fine del costruttore


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        String tag = "Persone di cognome ";
        titoloPagina = tag + getCognomeTxt();
    }// fine del metodo


    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    @Override
    protected String elaboraIncipitSpecifico() {
        String text = "incipit lista cognomi|cognome=" + getCognomeTxt();
        return LibWiki.setGraffe(text);
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    @Override
    protected void elaboraListaBiografie() {
        Cognome cognome = this.getCognome();

        if (cognome != null) {
            listaBio = cognome.listaBio();
        }// end of if cycle
    }// fine del metodo


    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaAntroCognomeABC(this, mappa);
    }// fine del metodo


    /**
     * Categorie al piede della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraCategorie() {
        return "[[Categoria:Liste di persone per cognome|" + getCognomeTxt() + "]]";
    }// fine del metodo


    public Cognome getCognome() {
        return (Cognome) getOggetto();
    }// end of getter method


    public String getCognomeTxt() {
        String cognomeTxt = "";
        Cognome cognome = getCognome();

        if (cognome != null) {
            cognomeTxt = cognome.getCognome();
        }// end of if cycle

        return cognomeTxt;
    }// end of getter method

}// fine della classe
