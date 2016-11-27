package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.lib.LibArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 * Crea la lista dei morti nel giorno e la carica sul server wiki
 */
public abstract class ListaGiorno extends ListaCrono {


    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiorno(Giorno giorno) {
        super(giorno);
    }// fine del costruttore



    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        Giorno giorno = getGiorno();

        if (giorno != null) {
            titoloPagina = giorno.getTitoloLista(getTagTitolo());
        }// fine del blocco if
    }// fine del metodo


    /**
     * Titolo della pagina 'madre'
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTitoloPaginaMadre() {
        String titolo = CostBio.VUOTO;
        Giorno giorno = this.getGiorno();

        if (giorno != null) {
            titolo += giorno.getTitolo();
        }// fine del blocco if

        return titolo;
    }// fine del metodo


    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Sovrascritto
     */
    @Override
    protected String elaboraTemplate(String testoIn) {
        return elaboraTemplate(testoIn, "Lista persone per giorno");
    }// fine del metodo

    /**
     * Recupera il singolo Giorno come ordinamento
     * Comprende il 29 febbraio per gli anni bisestili
     * Sovrascritto
     */
    @Override
    protected String getProgressivoCategoria() {
        String giornoTxt = CostBio.VUOTO;
        int giornoNumero = 0;
        Giorno giorno = getGiorno();
        String tag = "0";

        if (giorno != null) {
            giornoNumero = giorno.getOrdinamento();
        }// fine del blocco if

        if (giornoNumero > 0) {
            giornoTxt = CostBio.VUOTO + giornoNumero;
        }// fine del blocco if

        //--completamento a 3 cifre
        if (!giornoTxt.equals(CostBio.VUOTO)) {
            if (giornoTxt.length() == 1) {
                giornoTxt = tag + giornoTxt;
            }// fine del blocco if
            if (giornoTxt.length() == 2) {
                giornoTxt = tag + giornoTxt;
            }// fine del blocco if
        }// fine del blocco if

        return giornoTxt;
    }// fine del metodo


    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    protected String getTagTitolo() {
        return CostBio.VUOTO;
    }// fine del metodo


    public Giorno getGiorno() {
        return (Giorno) getOggetto();
    }// end of getter method

}// fine della classe
