package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gac on 23 nov 2016.
 * Crea la sottopagina della lista di attività indicata e la carica sul server wiki
 */
public class ListaAttivitaABC extends ListaAttivita {

    private HashMap<String, Object> mappaSuper;
    private String titoloVisibile;

    /**
     * Costruttore senza parametri
     */
    protected ListaAttivitaABC() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param attivita indicata
     */
    public ListaAttivitaABC(Attivita attivita) {
        super(attivita);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperAttivita della superPagina
     * @param mappaSuper        del paragrafo
     */
    public ListaAttivitaABC(ListaAttivita listaSuperAttivita, HashMap<String, Object> mappaSuper) {
        this.mappaSuper = mappaSuper;
        this.oggetto = listaSuperAttivita.getOggetto();
        this.titoloVisibile = (String) mappaSuper.get(KEY_MAP_TITOLO);
        doInit();
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

        // head
        usaHeadRitorno = true;
        usaHeadTocIndice = false;
        usaHeadIncipit = true;

        // body
        usaSottopagine = false;
        usaOrdineAlfabeticoParagrafi = true;
        tagParagrafoNullo = "...";
        usaTitoloParagrafoConLink = false;
        usaTaglioVociPagina = false;

    }// fine del metodo


    /**
     * Titolo della pagina 'madre'
     * <p>
     * Sovrascritto
     */
    protected String getTitoloPaginaMadre() {
        return "";
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per la pagina specifica
     * Esegue una query
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
        listaBio = (List<Bio>) mappaSuper.get(KEY_MAP_LISTA);
    }// fine del metodo

    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        return LibBio.getChiavePerCognome(bio, tagParagrafoNullo);
    }// fine del metodo


    @Override
    public String getAttivitaText() {
        return super.getAttivitaText() + "/" + titoloVisibile;
    }// fine del metodo

}// fine della classe
