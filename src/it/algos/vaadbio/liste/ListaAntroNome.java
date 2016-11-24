package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.genere.Genere;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.vaadbio.professione.Professione;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * Lista delle biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    protected ArrayList<Bio> getListaBio() {
        ArrayList<Bio> listaBio = null;
        Nome nome = this.getNome();

        if (nome != null) {
            listaBio = nome.bioNome();
        }// end of if cycle

        return listaBio;
    }// fine del metodo


    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaAntroNomeABC(this, mappa);
    }// fine del metodo


    /**
     * Piede della pagina
     * <p>
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
            text += "[[Categoria:Liste di persone per nome|" + getNomeTxt() + "]]";
        }// end of if cycle

        if (usaInclude) {
            text = CostBio.A_CAPO + LibBio.setNoIncludeMultiRiga(text);
        }// end of if cycle

        return text;
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
