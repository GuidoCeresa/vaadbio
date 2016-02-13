package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nell'anno e la carica sul server wiki
 * Crea la lista dei morti nell'anno e la carica sul server wiki
 */
public abstract class ListaAnno extends ListaCrono {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnno(Anno anno) {
        super(anno);
    }// fine del costruttore


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        Anno anno = getAnno();

        if (anno != null) {
            titoloPagina = anno.getTitoloLista(getTagTitolo());
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
        Anno anno = this.getAnno();

        if (anno != null) {
            titolo += anno.getTitolo();
        }// fine del blocco if

        return titolo;
    }// fine del metodo


    /**
     * Incapsula il testo come parametro di un (eventuale) template
     * Sovrascritto
     */
    @Override
    protected String elaboraTemplate(String testoIn) {
        return elaboraTemplate(testoIn, "Lista persone per anno");
    }// fine del metodo


    /**
     * Recupera il singolo Anno come progressivo dall'inizio
     */
    @Override
    protected String getProgressivoCategoria() {
        int annoProgressivo = 0;
        Anno anno = getAnno();

        if (anno != null) {
            annoProgressivo = anno.getOrdinamento();
        }// fine del blocco if

        return CostBio.VUOTO + annoProgressivo;
    }// fine del metodo

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    protected String getTagTitolo() {
        return CostBio.VUOTO;
    }// fine del metodo


    public Anno getAnno() {
        return (Anno) getOggetto();
    }// end of getter method

}// fine della classe
