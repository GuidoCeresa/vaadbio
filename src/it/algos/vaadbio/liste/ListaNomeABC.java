package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * /**
 * Created by gac on 27 dic 2015.
 * Crea la sottopagina della lista di persone per l'attività indicata e la carica sul server wiki
 */
public class ListaNomeABC extends ListaNome {

    private ArrayList<String> listaParagrafo;
    private String chiaveParagrafo;
    private String nomePersona;
    private String titoloParagrafo;

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
     * @param listaParagrafo  della superPagina
     * @param chiaveParagrafo della superPagina
     * @param nomePersona     della superPagina
     * @param titoloParagrafo della superPagina
     */
    public ListaNomeABC(ArrayList<String> listaParagrafo, String chiaveParagrafo, String titoloPaginaMadre, String nomePersona, String titoloParagrafo) {
        this.listaParagrafo = listaParagrafo;
        this.chiaveParagrafo = chiaveParagrafo;
        this.titoloPaginaMadre = titoloPaginaMadre;
        this.nomePersona = nomePersona;
        this.titoloParagrafo = titoloParagrafo;
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

    }// fine del metodo

    @Override
    public String getNomeTxt() {
        return nomePersona + "/" + titoloParagrafo;
    }// fine del metodo


    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
        int taglio = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 100);
        String lettera;
        ArrayList<String> lista;

        if (listaParagrafo != null && listaParagrafo.size() >= taglio) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (String didascalia : listaParagrafo) {
                lettera = getLettera(didascalia);
                if (mappaBiografie.containsKey(lettera)) {
                    lista = mappaBiografie.get(lettera);
                    lista.add(didascalia);
                } else {
                    lista = new ArrayList<>();
                    lista.add(didascalia);
                    mappaBiografie.put(lettera, lista);
                }// end of if/else cycle
            }// end of if cycle

            numPersone = listaParagrafo.size();
        }// end of for cycle

    }// fine del metodo

    /**
     * Estrae la prima lettera del cognome
     */
    protected String getLettera(String didascalia) {
        String lettera = CostBio.VUOTO;
        String tag = "[[";
        String nome = tag + nomePersona;
        int posIni = nome.length();
        String temp = didascalia.substring(posIni);
        temp = temp.trim();
        lettera = temp.substring(0, 1);

        return lettera;
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
        text += LibWiki.setBold(nomePersona);
        text += " e come attività principale sono ";
        text += chiaveParagrafo;

        return text;
    }// fine del metodo

}// fine della classe
