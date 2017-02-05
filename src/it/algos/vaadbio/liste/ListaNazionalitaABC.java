package it.algos.vaadbio.liste;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.domain.pref.Pref;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gac on 31/01/17.
 * Crea la sottopagina della lista di nazionalità indicata e la carica sul server wiki
 */
public class ListaNazionalitaABC extends ListaNazionalita {

    private HashMap<String, Object> mappaSuper;
    private String titoloVisibile;

    /**
     * Costruttore senza parametri
     */
    protected ListaNazionalitaABC() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param nazionalita indicata
     */
    public ListaNazionalitaABC(Nazionalita nazionalita) {
        super(nazionalita);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperNazionalita della superPagina
     * @param mappaSuper        del paragrafo
     */
    public ListaNazionalitaABC(ListaNazionalita listaSuperNazionalita, HashMap<String, Object> mappaSuper) {
        this.mappaSuper = mappaSuper;
        this.oggetto = listaSuperNazionalita.getOggetto();
        this.titoloVisibile = (String) mappaSuper.get(KEY_MAP_PARAGRAFO_TITOLO);
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
        usaSuddivisioneParagrafi = true;
        usaSottopagine = true;
        usaOrdineAlfabeticoParagrafi = true;
        tagParagrafoNullo = "...";
        usaTitoloParagrafoConLink = false;
        usaTaglioVociPagina = false;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);

    }// fine del metodo


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    @Override
    protected void elaboraTitolo() {
        titoloPagina = PROGETTO_BIOGRAFIE_NAZIONALITÀ+ getNazionalitaText();
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


    /**
     * Costruisce la sottopagina
     * Metodo sovrascritto
     */
    @Override
    protected void creaSottopagina(HashMap<String, Object> mappa) {
        new ListaNazionalitaLettera(this, mappa);
    }// fine del metodo

    @Override
    public String getNazionalitaText() {
        return super.getNazionalitaText() + "/" + titoloVisibile;
    }// fine del metodo

}// fine della classe
