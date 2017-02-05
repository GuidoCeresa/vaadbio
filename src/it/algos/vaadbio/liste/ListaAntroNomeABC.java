package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;

import java.util.HashMap;
import java.util.List;

/**
 * /**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaAntroNomeABC extends ListaAntroNome {

    private HashMap<String, Object> mappaSuper;
    private String titoloVisibile;


    /**
     * Costruttore
     *
     * @param nome dell'antroponimo
     */
    public ListaAntroNomeABC(Nome nome) {
        super(nome);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperNome della superPagina
     * @param mappaSuper          del paragrafo
     */
    public ListaAntroNomeABC(ListaAntroNome listaSuperNome, HashMap<String, Object> mappaSuper) {
        this.mappaSuper = mappaSuper;
        this.oggetto = listaSuperNome.getOggetto();
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
        return "Persone di nome " + super.getNomeTxt();
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
        text += "Questa è una lista di persone presenti nell'enciclopedia che hanno il prenome ";
        text += LibWiki.setBold(super.getNomeTxt());
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
        return LibBio.getChiavePerCognome(bio, tagParagrafoNullo);
    }// fine del metodo




    @Override
    public String getNomeTxt() {
        return super.getNomeTxt() + "/" + titoloVisibile;
    }// fine del metodo

}// fine della classe
