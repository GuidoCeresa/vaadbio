package it.algos.vaadbio.liste;

import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 23 nov 2016.
 * .
 */
public class ListaAttivitaABC extends ListaAttivita {

    private ListaAttivita listaSuperAttivita;
    private String paginaLinkata;
    private String titoloVisibile;
    private String sesso;
    private ArrayList listaDidascalie;

    /**
     * Costruttore
     */
    public ListaAttivitaABC(Attivita attivita) {
        super(attivita);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param listaSuperAttivita della superPagina
     * @param mappa              del paragrafo
     */
    public ListaAttivitaABC(ListaAttivita listaSuperAttivita, HashMap<String, Object> mappa) {
        this.listaSuperAttivita = listaSuperAttivita;
        this.oggetto = listaSuperAttivita.getOggetto();
        this.paginaLinkata = (String) mappa.get(KEY_MAP_LINK);
        this.titoloVisibile = (String) mappa.get(KEY_MAP_TITOLO);
        this.listaDidascalie = (ArrayList) mappa.get(KEY_MAP_LISTA);
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
        return "Progetto:Biografie/Attività/" + super.getPluraleAttività();
    }// fine del metodo


    @Override
    public String getPluraleAttività() {
        return super.getPluraleAttività() + "/" + titoloVisibile;
    }// fine del metodo


    /**
     * Lista delle biografie che hanno una valore valido per il link specifico
     */
    protected ArrayList getListaBioSottoPagina() {
        ArrayList lista = null;
//        String tagOR = " or ";
//        String query;
//        String queryBase = "select bio.cognome,bio.didascaliaListe from Bio bio";
//        String queryWhere = " where bio.cognome='" + getCognome().getCognome()+"'";
//        String queryWhereOR = CostBio.VUOTO;
//        String queryOrder = " order by bio.cognome asc";
//        ArrayList<Long> listaAttivitaID = Attivita.findAllSingolari(titoloVisibile, sesso);
//
//        if (listaAttivitaID != null && listaAttivitaID.size() > 0) {
//            queryWhereOR += " and (";
//            for (long attivitaID : listaAttivitaID) {
//                queryWhereOR += "bio.attivitaPunta.id=" + attivitaID + tagOR;
//            }// end of for cycle
//            queryWhereOR = LibText.levaCoda(queryWhereOR, tagOR);
//            queryWhereOR = " " + queryWhereOR + ")";
//        } else {
//            if (titoloVisibile.equals(listaSuperCognome.tagParagrafoNullo)) {
//                queryWhereOR = " and bio.attivitaPunta.id=null";
//            }// end of if cycle
//        }// end of if/else cycle
//
//        query = queryBase + queryWhere + queryWhereOR + queryOrder;
//        lista = LibBio.queryFind(query);
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
//        text += "Questa è una lista di persone presenti nell'enciclopedia che hanno il cognome ";
//        text += LibWiki.setBold(super.getCognomeTxt());
//        text += " e come attività principale sono ";
//        text += LibWiki.setBold(titoloVisibile);

        return text;
    }// fine del metodo

}// fine della classe
