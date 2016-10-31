package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * /**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaAntroNomeABC extends ListaAntroNome {

    private ListaAntroNome listaSuperNome;
    private String paginaLinkata;
    private String titoloVisibile;
    private String sesso;

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
     * @param mappa          del paragrafo
     */
    public ListaAntroNomeABC(ListaAntroNome listaSuperNome, HashMap<String, Object> mappa) {
        this.listaSuperNome = listaSuperNome;
        this.oggetto = listaSuperNome.getOggetto();
        this.paginaLinkata = (String) mappa.get(KEY_MAP_LINK);
        this.titoloVisibile = (String) mappa.get(KEY_MAP_TITOLO);
        this.sesso = (String) mappa.get(KEY_MAP_SESSO);
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


    @Override
    public String getNomeTxt() {
        return super.getNomeTxt() + "/" + titoloVisibile;
    }// fine del metodo

    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    protected void elaboraMappaBiografie() {
        ArrayList lista = getListaBioSottoPagina();

        if (usaTaglioVociPagina && lista.size() < maxVociPagina) {
            return;
        }// end of if cycle

        if (lista != null && lista.size() > 0) {
            for (Object mappa : lista) {
                elaboraMappa(mappa);
            }// end of if cycle
        }// end of for cycle

        if (lista != null) {
            numPersone = lista.size();
        }// end of if cycle

    }// fine del metodo


    /**
     * Lista delle biografie che hanno una valore valido per il link specifico
     */
    protected ArrayList getListaBioSottoPagina() {
        ArrayList lista;
        String tagOR = " or ";
        String query;
        String queryBase = "select bio.cognome,bio.didascaliaListe from Bio bio";
        String queryWhere = " where bio.nomePunta.id=" + getNome().getId();
        String queryWhereOR = CostBio.VUOTO;
        String queryOrder = " order by bio.cognome asc";
        ArrayList<Long> listaAttivitaID = Attivita.findAllSingolari(titoloVisibile, sesso);

        if (listaAttivitaID != null && listaAttivitaID.size() > 0) {
            queryWhereOR += " and (";
            for (long attivitaID : listaAttivitaID) {
                queryWhereOR += "bio.attivitaPunta.id=" + attivitaID + tagOR;
            }// end of for cycle
            queryWhereOR = LibText.levaCoda(queryWhereOR, tagOR);
            queryWhereOR = " " + queryWhereOR + ")";
        } else {
            if (titoloVisibile.equals(listaSuperNome.tagParagrafoNullo)) {
                queryWhereOR = " and bio.attivitaPunta.id=null";
            }// end of if cycle
        }// end of if/else cycle

        query = queryBase + queryWhere + queryWhereOR + queryOrder;
        lista = LibBio.queryFind(query);
        return lista;
    }// fine del metodo

    /**
     * Costruisce una singola mappa
     */
    protected void elaboraMappa(Object obj) {
        ArrayList<String> lista;
        String cognome;
        String key;
        String didascalia;
        HashMap<String, Object> mappa;

        cognome = (String) ((Object[]) obj)[0];
        didascalia = (String) ((Object[]) obj)[1];
        if (!cognome.equals(CostBio.VUOTO)) {
            key = cognome.substring(0, 1);
            if (Pref.getBool(CostBio.USA_CASE_UGUALI, false)) {
                key = key.toUpperCase();
            }// end of if cycle
        } else {
            key = tagParagrafoNullo;
        }// end of if/else cycle

        if (mappaBio.containsKey(key)) {
            lista = (ArrayList<String>) mappaBio.get(key).get(KEY_MAP_LISTA);
            lista.add(didascalia);
        } else {
            mappa = new HashMap<String, Object>();
            lista = new ArrayList<>();
            lista.add(didascalia);
            mappa.put(KEY_MAP_TITOLO, key);
            mappa.put(KEY_MAP_LISTA, lista);
            mappaBio.put(key, mappa);
        }// end of if/else cycle
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

}// fine della classe
