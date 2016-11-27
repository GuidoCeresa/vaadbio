package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaAntroCognomeABC extends ListaAntroCognome {


    private HashMap<String, Object> mappaSuper;
    private String titoloVisibile;


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
     * @param mappaSuper        del paragrafo
     */
    public ListaAntroCognomeABC(ListaAntroCognome listaSuperCognome, HashMap<String, Object> mappaSuper) {
        this.mappaSuper = mappaSuper;
        this.oggetto = listaSuperCognome.getOggetto();
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
        return "Persone di cognome " + super.getCognomeTxt();
    }// fine del metodo


    /**
     * Costruisce la frase di incipit iniziale
     * <p>
     * Sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    @Override
    protected String elaboraIncipitSpecifico() {
        String text = CostBio.VUOTO;
        text += "Questa è una lista di persone presenti nell'enciclopedia che hanno il cognome ";
        text += LibWiki.setBold(super.getCognomeTxt());
        text += " e come attività principale sono ";
        text += LibWiki.setBold(titoloVisibile);

        return text;
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
        return LibBio.getChiavePerNome(bio, tagParagrafoNullo);
    }// fine del metodo


    @Override
    public String getCognomeTxt() {
        return super.getCognomeTxt() + "/" + titoloVisibile;
    }// fine del metodo

}// fine della classe
