package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 16 mag 2016.
 * Crea la lista delle persone col cognome indicato e la carica sul server wiki
 */
public class ListaAntroCognome extends ListaAntroponimo {


    /**
     * Costruttore senza parametri
     */
    protected ListaAntroCognome() {
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
    protected void elaboraListaBiografie() {
        Cognome cognome = getCognome();

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
     * Piede della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraFooter() {
        String text = CostBio.VUOTO;
        boolean usaInclude = usaFooterPortale || usaFooterCategorie;

        if (usaFooterPortale) {
            text += CostBio.A_CAPO;
            text += "{{Portale|antroponimi}}";
        }// end of if cycle

        if (usaFooterCategorie) {
            text += CostBio.A_CAPO;
            text += "[[Categoria:Liste di persone per cognome|" + getCognomeTxt() + "]]";
        }// end of if cycle

        if (usaInclude) {
            text = CostBio.A_CAPO + LibBio.setNoIncludeMultiRiga(text);
        }// end of if cycle

        return text;
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
