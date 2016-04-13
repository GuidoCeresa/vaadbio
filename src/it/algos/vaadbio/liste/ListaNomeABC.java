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
public class ListaNomeABC extends ListaNome {

    private String paginaLinkata;
    private String titoloVisibile;
    private String sesso;

    /**
     * Costruttore
     *
     * @param nome dell'antroponimo
     */
    public ListaNomeABC(Nome nome) {
        super(nome);
    }// fine del costruttore


    /**
     * Costruttore
     *
     * @param nome  della superPagina
     * @param mappa del paragrafo
     */
    public ListaNomeABC(Nome nome, HashMap<String, Object> mappa) {
        this.oggetto = nome;
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
    @Override
    protected void elaboraMappaBiografie() {
        int taglio = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);
        ArrayList<String> lista;
        ArrayList nuovaLista;
        String tagOR = " or ";
        String query = "select bio.cognome,bio.didascaliaListe from Bio bio";
        String where = " where bio.nomePunta.id=" + getNome().getId();
        String whereOR = CostBio.VUOTO;
        String order = " order by bio.cognome asc";
        ArrayList<Long> listaAttivitaID = Attivita.findAllSingolari(titoloVisibile, sesso);
        String cognome;
        String key;
        String didascalia;
        HashMap<String, Object> mappa;

        if (listaAttivitaID != null && listaAttivitaID.size() > 0) {
            where += " and (";
            for (long attivitaID : listaAttivitaID) {
                whereOR += "bio.attivitaPunta.id=" + attivitaID + tagOR;
            }// end of for cycle
            whereOR = LibText.levaCoda(whereOR, tagOR);
            whereOR += ")";
        }// end of if cycle

        nuovaLista = LibBio.queryFind(query + where + whereOR + order);
        if (nuovaLista != null && nuovaLista.size() >= taglio) {
            for (Object obj : nuovaLista) {

                cognome = (String) ((Object[]) obj)[0];
                didascalia = (String) ((Object[]) obj)[1];
                if (!cognome.equals(CostBio.VUOTO)) {
                    key = cognome.substring(0, 1);
                } else {
                    key = tagParagrafoNullo;
                }// end of if/else cycle

//                if (mappaBiografie.containsKey(key)) {
//                    lista = mappaBiografie.get(key);
//                    lista.add(didascalia);
//                } else {
//                    lista = new ArrayList<>();
//                    lista.add(didascalia);
//                    mappaBiografie.put(key, lista);
//                }// end of if/else cycle


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

            }// end of for cycle
            numPersone = nuovaLista.size();
        }// end of if cycle

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
